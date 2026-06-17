package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
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
    val planetNameState by viewModel.planetName.collectAsState()

    LaunchedEffect(character.id) {
        viewModel.loadPlanetName(character.homeworld)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_confidential),
            contentDescription = "Watermark",
            alpha = 0.35f,
            colorFilter = ColorFilter.tint(Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .size(320.dp)
                .align(Alignment.Center)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = character.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 70.dp)
            ) {
                Text(text = "Altura: ${character.height}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Gênero: ${character.gender}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Peso: ${character.mass}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Cor do Cabelo: ${character.hairColor}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Cor da Pele: ${character.skinColor}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Cor do Olho: ${character.eyeColor}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Ano de Nascimento: ${character.birthYear}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Planeta Natal: $planetNameState", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(text = "Espécie: ${character.species}", fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_sithjedi),
            contentDescription = "Favorite Faction",
            colorFilter = ColorFilter.tint(Color(0xFF616161)),
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_new_empire),
            contentDescription = "Confidential Insignia",
            alpha = 0.8f,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp)
        )
    }
}