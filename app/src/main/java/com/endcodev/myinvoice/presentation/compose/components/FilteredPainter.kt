package com.endcodev.myinvoice.presentation.compose.components

import android.net.Uri
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter

data class FilteredImageModel(
    val image: Painter,
    val filter: ColorFilter?,
)

@Composable
fun filteredImage(image: Uri?, defaultImage: Painter): FilteredImageModel {
    var colorFilter: ColorFilter? = null
    var painter: Painter? = uriToPainterImage(image)

    if (painter == null) {
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        painter = defaultImage

    }
    return FilteredImageModel(image = painter, filter = colorFilter)
}