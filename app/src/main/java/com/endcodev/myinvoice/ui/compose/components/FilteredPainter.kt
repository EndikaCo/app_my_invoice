package com.endcodev.myinvoice.ui.compose.components

import android.net.Uri
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.endcodev.myinvoice.R

data class FilteredImageModel(
    val image: Painter,
    val filter: ColorFilter?,
)

@Composable
fun filteredImage(image: Uri?): FilteredImageModel {
    var colorFilter: ColorFilter? = null
    var painter: Painter? = uriToPainterImage(image)

    if (painter == null) {
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        painter = painterResource(id = R.drawable.no_photo_24)

    }
    return FilteredImageModel(image = painter, filter = colorFilter)
}