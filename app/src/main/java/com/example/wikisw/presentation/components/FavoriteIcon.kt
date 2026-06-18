package com.example.wikisw.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wikisw.R
import com.example.wikisw.presentation.ui.theme.RebelBlue
import com.example.wikisw.presentation.ui.theme.TextTertiary

/**
 * A reusable favorite (Sith/Jedi) icon component.
 *
 * @param isFavorite Whether the item is currently favorited.
 * @param onClick Action to perform when the icon is clicked.
 * @param modifier Modifier to be applied to the icon.
 */
@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_sithjedi),
        contentDescription = "Toggle Favorite",
        colorFilter = ColorFilter.tint(
            if (isFavorite) RebelBlue else TextTertiary
        ),
        modifier = modifier
            .size(36.dp)
            .clickable { onClick() }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF16161A)
@Composable
fun FavoriteIconFavoritePreview() {
    FavoriteIcon(isFavorite = true, onClick = {})
}

@Preview(showBackground = true, backgroundColor = 0xFF16161A)
@Composable
fun FavoriteIconNotFavoritePreview() {
    FavoriteIcon(isFavorite = false, onClick = {})
}
