package com.example.wikisw.presentation.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikisw.R
import com.example.wikisw.domain.model.Character
import com.example.wikisw.presentation.components.ConfidentialStamp
import com.example.wikisw.presentation.components.EmpireLogoWatermark
import com.example.wikisw.presentation.components.FavoriteIcon
import com.example.wikisw.presentation.components.ImperialTopBar
import com.example.wikisw.presentation.components.InfoRow
import com.example.wikisw.presentation.ui.theme.DarkBackground
import com.example.wikisw.presentation.ui.theme.RebelBlue
import com.example.wikisw.presentation.ui.theme.SurfaceDark
import com.example.wikisw.presentation.ui.theme.BorderGray
import com.example.wikisw.presentation.ui.theme.TextTertiary
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterInfoScreen(
    characterId: Int,
    onBackClick: () -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val character by viewModel.currentCharacter.collectAsState(initial = null)
    val details by viewModel.detailsState.collectAsState()
    val planetName = details.first
    val speciesName = details.second

    LaunchedEffect(characterId) {
        viewModel.observeCharacter(characterId)
    }

    LaunchedEffect(character) {
        character?.let {
            viewModel.loadDetailsInParallel(it.homeworld, it.species)
        }
    }

    CharacterInfoScreenContent(
        character = character,
        planetName = planetName,
        speciesName = speciesName,
        onBackClick = onBackClick,
        onToggleFavorite = { char -> viewModel.toggleFavorite(char.id, char.isFavorite) }
    )
}

@Composable
fun CharacterInfoScreenContent(
    character: Character?,
    planetName: String,
    speciesName: String,
    onBackClick: () -> Unit,
    onToggleFavorite: (Character) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .navigationBarsPadding()
    ) {
        character?.let { char ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ImperialTopBar(
                    subtitle = char.name.uppercase(),
                    onBackClick = onBackClick,
                    modifier = Modifier.statusBarsPadding()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    CharacterDetailsCard(
                        char = char,
                        planetName = planetName,
                        speciesName = speciesName,
                        onToggleFavorite = { onToggleFavorite(char) }
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    ConfidentialStamp()

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            EmpireLogoWatermark(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun CharacterDetailsCard(
    char: Character,
    planetName: String,
    speciesName: String,
    onToggleFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, shape = RoundedCornerShape(8.dp))
            .border(1.dp, BorderGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        InfoRow(stringResource(R.string.label_height), char.height)
        InfoRow(stringResource(R.string.label_gender), char.gender)
        InfoRow(stringResource(R.string.label_mass), char.mass)
        InfoRow(stringResource(R.string.label_hair), char.hairColor)
        InfoRow(stringResource(R.string.label_skin), char.skinColor)
        InfoRow(stringResource(R.string.label_eyes), char.eyeColor)
        InfoRow(stringResource(R.string.label_birth), char.birthYear)
        InfoRow(stringResource(R.string.label_planet), planetName)
        InfoRow(stringResource(R.string.label_species), speciesName)

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            thickness = 1.dp,
            color = BorderGray
        )

        FavoriteAction(
            isFavorite = char.isFavorite,
            onToggleFavorite = onToggleFavorite
        )
    }
}

@Composable
fun FavoriteAction(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleFavorite() }
            .padding(vertical = 4.dp)
    ) {
        FavoriteIcon(
            isFavorite = isFavorite,
            onClick = onToggleFavorite
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = if (isFavorite) stringResource(R.string.record_favorited) else stringResource(R.string.favorite_record),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (isFavorite) RebelBlue else TextTertiary
        )
    }
}

@Preview
@Composable
fun CharacterInfoScreenPreview() {
    CharacterInfoScreenContent(
        character = Character(1, "Luke Skywalker", "172", "male", "77", "blond", "fair", "blue", "19BBY", "", "", false),
        planetName = "Tatooine",
        speciesName = "Human",
        onBackClick = {},
        onToggleFavorite = {}
    )
}
