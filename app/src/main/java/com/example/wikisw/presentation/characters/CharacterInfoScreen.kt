package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikisw.R
import com.example.wikisw.domain.model.Character
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterInfoScreen(
    character: Character,
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val details by viewModel.detailsState.collectAsState()
    val planetName = details.first
    val speciesName = details.second

    LaunchedEffect(character.id) {
        viewModel.loadDetailsInParallel(character.homeworld, character.species)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_confidential),
            contentDescription = null,
            alpha = 0.35f,
            colorFilter = ColorFilter.tint(Color.Red),
            modifier = Modifier.fillMaxWidth().size(320.dp).align(Alignment.Center)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = character.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 80.dp)
            ) {
                Text(text = "Altura: ${character.height}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Gênero: ${character.gender}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Peso: ${character.mass}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Cor do Cabelo: ${character.hairColor}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Cor da Pele: ${character.skinColor}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Cor do Olho: ${character.eyeColor}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Ano de Nascimento: ${character.birthYear}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))

                Text(text = "Planeta Natal: $planetName", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Espécie: $speciesName", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable { viewModel.toggleFavorite(character.id, character.isFavorite) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sithjedi),
                contentDescription = "Favoritar",
                colorFilter = ColorFilter.tint(
                    if (character.isFavorite) Color.Blue else Color.Gray
                ),
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Favoritar",
                fontSize = 12.sp,
                color = if (character.isFavorite) Color.Blue else Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_new_empire),
            contentDescription = null,
            alpha = 0.8f,
            modifier = Modifier.size(120.dp).align(Alignment.BottomCenter).padding(bottom = 15.dp)
        )
    }
}