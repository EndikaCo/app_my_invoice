package com.endcodev.myinvoice.presentation.compose.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter

@Composable
fun uriToPainterImage(uri: Uri?): Painter? {
    return if (uri == null || uri.toString() == "null")
        null
    else
        rememberAsyncImagePainter(model = uri)
}