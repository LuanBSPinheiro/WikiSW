package com.example.wikisw.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikisw.presentation.ui.theme.EmpireRed

/**
 * A standard row for displaying label-value pairs in character details.
 *
 * @param label The category or attribute name.
 * @param value The attribute value.
 */
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
            color = EmpireRed,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF16161A)
@Composable
fun InfoRowPreview() {
    InfoRow(label = "HEIGHT", value = "172")
}
