package com.endcodev.myinvoice.presentation.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme

@Composable
fun MyActionButtons(
    enableAccept: Boolean,
    onAcceptClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Button(onClick = { onCancelClick() }, enabled = enableAccept) {
            Icon(Icons.Default.Close, contentDescription = "Action Cancel")
        }
        Button(onClick = { onAcceptClick() }, enabled = enableAccept) {
            Icon(Icons.Default.Check, contentDescription = "Action Accept")
        }
    }
}

@Preview(name = "ENABLED - Light Mode", showBackground = true, showSystemUi = true)
@Preview(name = "ENABLED - Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAcceptCancelButtons() {
    MyInvoiceTheme {
        Scaffold(
            content = {
                Text(text = "", Modifier.padding(it))
            },
            bottomBar = {
                MyActionButtons(
                    enableAccept = false,
                    onAcceptClick = {},
                    onCancelClick = {}
                )
            }
        )
    }
}
