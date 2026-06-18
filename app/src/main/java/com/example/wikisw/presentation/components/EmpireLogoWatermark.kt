package com.example.wikisw.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wikisw.R
import com.example.wikisw.presentation.ui.theme.EmpireRed

/**
 * A decorative watermark component showing the Imperial logo.
 * Typically used as a background or bottom-aligned brand element.
 *
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
fun EmpireLogoWatermark(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_new_empire),
        contentDescription = null,
        colorFilter = ColorFilter.tint(EmpireRed.copy(alpha = 0.15f)),
        modifier = modifier
            .size(100.dp)
            .padding(bottom = 16.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0F11)
@Composable
fun EmpireLogoWatermarkPreview() {
    EmpireLogoWatermark()
}
