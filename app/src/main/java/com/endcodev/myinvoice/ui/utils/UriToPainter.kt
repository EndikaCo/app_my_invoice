package com.endcodev.myinvoice.ui.utils

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter

//convert Uri to Painter
@Composable
fun uriToPainterImage(uri: Uri?, default: Painter): Painter {
    if (uri == null)
        return default
    return rememberAsyncImagePainter(model = uri)
}

@Composable
fun uriToPainterImage(uri: Uri?): Painter? {
    return if (uri == null)
        null
    else
        rememberAsyncImagePainter(model = uri)
}