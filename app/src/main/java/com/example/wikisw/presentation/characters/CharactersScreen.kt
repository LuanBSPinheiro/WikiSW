package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wikisw.R
import com.example.wikisw.domain.model.Character
import com.example.wikisw.presentation.components.ImperialTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharactersScreen(
    onCharacterClick: (Character) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val onlyFavorites by viewModel.onlyFavorites.collectAsState()

    Scaffold(
        containerColor = Color(0xFF0F0F11)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            ImperialTopBar(
                subtitle = "Galactic Records"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    placeholder = { Text("Filtrar registros...", color = Color(0xFF71717A)) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFF71717A)
                        )
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(6.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF16161A),
                        unfocusedContainerColor = Color(0xFF16161A),
                        focusedBorderColor = Color(0xFFE53935),
                        unfocusedBorderColor = Color(0xFF27272A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(54.dp)
                        .background(
                            color = if (onlyFavorites) Color(0xFF0066FF).copy(alpha = 0.15f) else Color(
                                0xFF16161A
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable { viewModel.onToggleFilterFavorites() }
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sithjedi),
                        contentDescription = "Filter Favorites",
                        colorFilter = ColorFilter.tint(
                            if (onlyFavorites) Color(0xFF0066FF) else Color(0xFF71717A)
                        ),
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp)
            ) {
                when (val state = uiState) {
                    is CharactersUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFFE53935)
                        )
                    }

                    is CharactersUiState.Success -> {
                        if (state.characters.isEmpty()) {
                            Text(
                                text = "Nenhum registro imperial correspondente.",
                                color = Color(0xFF71717A),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(state.characters) { character ->
                                    CharacterItem(
                                        character = character,
                                        onItemClick = onCharacterClick,
                                        onToggleFavorite = {
                                            viewModel.toggleFavorite(
                                                it.id,
                                                it.isFavorite
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }

                    is CharactersUiState.Error -> {
                        Text(
                            text = state.message,
                            color = Color(0xFFE53935),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}