package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CharacterItem(
    character: Character,
    onItemClick: (Character) -> Unit,
    onToggleFavorite: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(145.dp)
            .clickable { onItemClick(character) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E24)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2C2C35)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_sithjedi),
                    contentDescription = "Toggle Favorite",
                    colorFilter = ColorFilter.tint(
                        if (character.isFavorite) Color(0xFF0066FF) else Color(0xFF52525B)
                    ),
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.TopEnd)
                        .clickable { onToggleFavorite(character) }
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Altura: ${character.height}", fontSize = 12.sp, color = Color(0xFFA0A0AB), textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 0.5.dp))
                Text(text = "Gênero: ${character.gender}", fontSize = 12.sp, color = Color(0xFFA0A0AB), textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 0.5.dp))
                Text(text = "Peso: ${character.mass}", fontSize = 12.sp, color = Color(0xFFA0A0AB), textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 0.5.dp))
            }
        }
    }
}