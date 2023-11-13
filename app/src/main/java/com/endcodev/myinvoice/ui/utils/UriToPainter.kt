package com.endcodev.myinvoice.ui.utils

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter

@Composable
fun uriToPainterImage(uri: Uri?): Painter? {
    return if (uri == null)
        null
    else
        rememberAsyncImagePainter(model = uri)
}