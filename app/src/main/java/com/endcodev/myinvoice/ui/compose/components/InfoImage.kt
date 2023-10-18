package com.endcodev.myinvoice.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun InfoImage(size :Int, image: Painter, modifier: Modifier) {
    Image(
        painter = image,
        contentDescription = "logo",
        modifier = modifier
            .width(size.dp)
            .height(size.dp)
    )
}