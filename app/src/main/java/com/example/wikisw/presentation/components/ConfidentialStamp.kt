package com.example.wikisw.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wikisw.R
import com.example.wikisw.presentation.ui.theme.EmpireRed

/**
 * A decorative stamp component showing a "Confidential" label.
 *
 * @param modifier Modifier to be applied to the outer Box.
 */
@Composable
fun ConfidentialStamp(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_confidential),
            contentDescription = null,
            colorFilter = ColorFilter.tint(EmpireRed.copy(alpha = 0.4f)),
            modifier = Modifier
                .size(200.dp)
                .rotate(-15f)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0F11)
@Composable
fun ConfidentialStampPreview() {
    ConfidentialStamp()
}
