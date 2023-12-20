package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun MyFloatingButton(
    modifier: Modifier,
    painter: Painter,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
        modifier = modifier,
        shape = CircleShape
    ) {
        Icon(
            painter = painter,
            contentDescription = "Add to the list",
            tint = Color.Black
        )
    }
}

@Preview(name = "Light Mode", backgroundColor = 0xFFC2C2C2)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewFloatingActionButton() {
    MyInvoiceTheme {
        MyFloatingButton(
            modifier = Modifier,
            painter = painterResource(R.drawable.ic_launcher_foreground),
            onClick = {}
        )
    }
}
