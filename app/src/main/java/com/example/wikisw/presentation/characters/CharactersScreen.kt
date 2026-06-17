package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    placeholder = { Text("Buscar personagem...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { viewModel.onToggleFilterFavorites() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sithjedi),
                        contentDescription = "Filter Favorites",
                        colorFilter = ColorFilter.tint(
                            if (onlyFavorites) Color.Blue else Color.Red
                        ),
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = uiState) {
                    is CharactersUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is CharactersUiState.Success -> {
                        if (state.characters.isEmpty()) {
                            Text("Nenhum personagem encontrado.", Modifier.align(Alignment.Center))
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                            state.message,
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}