package com.endcodev.myinvoice.ui.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomButtons(
    enabled: Boolean,
    onAcceptClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        RectangleButton("Cancel", Modifier.weight(1F), true, onCancelClick)
        Spacer(modifier = Modifier.width(25.dp))
        RectangleButton("Accept", Modifier.weight(1F), enabled,  onAcceptClick)
    }
}

@Composable
fun RectangleButton(text: String, modifier: Modifier, enabled: Boolean, onButtonClick: () -> Unit) {
    Button(
        onClick = { onButtonClick() },
        modifier = modifier.height(50.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(text = text)
    }
}