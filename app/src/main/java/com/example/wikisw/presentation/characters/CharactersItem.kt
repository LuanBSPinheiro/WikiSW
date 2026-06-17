package com.example.wikisw.presentation.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(character) }
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = character.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 60.dp)
            ) {
                Text(text = "Altura: ${character.height}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Gênero: ${character.gender}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Peso: ${character.mass}", fontSize = 14.sp, color = Color.Gray)
            }

            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                thickness = 0.5.dp,
                color = Color.Black
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_sithjedi),
            contentDescription = "Favorite Icon",
            colorFilter = ColorFilter.tint(
                if (character.isFavorite) Color.Blue else Color(0xFF616161)
            ),
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clickable { onToggleFavorite(character) }
        )
    }
}