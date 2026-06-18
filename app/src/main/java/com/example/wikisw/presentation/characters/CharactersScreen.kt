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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wikisw.R
import com.example.wikisw.domain.model.Character
import com.example.wikisw.presentation.components.ImperialTopBar
import com.example.wikisw.presentation.ui.theme.DarkBackground
import com.example.wikisw.presentation.ui.theme.EmpireRed
import com.example.wikisw.presentation.ui.theme.RebelBlue
import com.example.wikisw.presentation.ui.theme.SurfaceDark
import com.example.wikisw.presentation.ui.theme.TextSecondary
import com.example.wikisw.presentation.ui.theme.BorderGray
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

    CharactersScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onlyFavorites = onlyFavorites,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onToggleFilterFavorites = viewModel::onToggleFilterFavorites,
        onCharacterClick = onCharacterClick,
        onToggleFavorite = { viewModel.toggleFavorite(it.id, it.isFavorite) },
        modifier = modifier
    )
}

@Composable
fun CharactersScreenContent(
    uiState: CharactersUiState,
    searchQuery: String,
    onlyFavorites: Boolean,
    onSearchQueryChanged: (String) -> Unit,
    onToggleFilterFavorites: () -> Unit,
    onCharacterClick: (Character) -> Unit,
    onToggleFavorite: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = DarkBackground,
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            ImperialTopBar(
                subtitle = stringResource(R.string.galactic_records)
            )

            SearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChanged,
                onlyFavorites = onlyFavorites,
                onToggleFavorites = onToggleFilterFavorites
            )

            CharactersContent(
                uiState = uiState,
                onCharacterClick = onCharacterClick,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onlyFavorites: Boolean,
    onToggleFavorites: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text(stringResource(R.string.filter_records), color = TextSecondary) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = TextSecondary
                )
            },
            modifier = Modifier.weight(1f),
            singleLine = true,
            shape = RoundedCornerShape(6.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceDark,
                unfocusedContainerColor = SurfaceDark,
                focusedBorderColor = EmpireRed,
                unfocusedBorderColor = BorderGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(54.dp)
                .background(
                    color = if (onlyFavorites) RebelBlue.copy(alpha = 0.15f) else SurfaceDark,
                    shape = RoundedCornerShape(6.dp)
                )
                .clickable { onToggleFavorites() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sithjedi),
                contentDescription = stringResource(R.string.filter_favorites),
                colorFilter = ColorFilter.tint(
                    if (onlyFavorites) RebelBlue else TextSecondary
                ),
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
fun CharactersContent(
    uiState: CharactersUiState,
    onCharacterClick: (Character) -> Unit,
    onToggleFavorite: (Character) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp)
    ) {
        when (uiState) {
            is CharactersUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = EmpireRed
                )
            }

            is CharactersUiState.Success -> {
                if (uiState.characters.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_records_found),
                        color = TextSecondary,
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
                        items(uiState.characters) { character ->
                            CharacterItem(
                                character = character,
                                onItemClick = onCharacterClick,
                                onToggleFavorite = { onToggleFavorite(character) }
                            )
                        }
                    }
                }
            }

            is CharactersUiState.Error -> {
                Text(
                    text = uiState.message,
                    color = EmpireRed,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun CharactersScreenPreview() {
    CharactersScreenContent(
        uiState = CharactersUiState.Success(
            listOf(
                Character(1, "Luke Skywalker", "172", "male", "77", "blond", "fair", "blue", "19BBY", "", "", false),
                Character(2, "C-3PO", "167", "n/a", "75", "n/a", "gold", "yellow", "112BBY", "", "", true)
            )
        ),
        searchQuery = "",
        onlyFavorites = false,
        onSearchQueryChanged = {},
        onToggleFilterFavorites = {},
        onCharacterClick = {},
        onToggleFavorite = {}
    )
}
