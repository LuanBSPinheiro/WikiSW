package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikisw.R
import com.example.wikisw.presentation.components.ImperialTopBar
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F11))
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF16161A), shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFF27272A), shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        InfoRow("ALTURA", char.height)
                        InfoRow("GÊNERO", char.gender)
                        InfoRow("PESO", char.mass)
                        InfoRow("CABELO", char.hairColor)
                        InfoRow("PELE", char.skinColor)
                        InfoRow("OLHOS", char.eyeColor)
                        InfoRow("NASCIMENTO", char.birthYear)
                        InfoRow("PLANETA", planetName)
                        InfoRow("ESPÉCIE", speciesName)

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            thickness = 1.dp,
                            color = Color(0xFF27272A)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.toggleFavorite(char.id, char.isFavorite) }
                                .padding(vertical = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_sithjedi),
                                contentDescription = "Favoritar",
                                colorFilter = ColorFilter.tint(
                                    if (char.isFavorite) Color(0xFF0066FF) else Color(0xFF52525B)
                                ),
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (char.isFavorite) "REGISTRO FAVORITADO" else "FAVORITAR REGISTRO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (char.isFavorite) Color(0xFF0066FF) else Color(0xFF52525B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_confidential),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color(0xFFE53935).copy(alpha = 0.4f)),
                            modifier = Modifier
                                .size(200.dp)
                                .rotate(-15f)
                        )
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_new_empire),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFFE53935).copy(alpha = 0.15f)),
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color(0xFFE53935),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}