package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun MyBottomBar(
    enableDelete: Boolean,
    enableSave: Boolean,
    onDeleteClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onAcceptClick: () -> Unit,
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { onDeleteClick() }, enabled = enableDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Action 1")
            }
            IconButton(onClick = { onAddItemClick() }) {
                Icon(Icons.Default.PlaylistAdd, contentDescription = "Action 2")
            }
            IconButton(onClick = { onAcceptClick() }, enabled = enableSave) {
                Icon(Icons.Default.Save, contentDescription = "Action 3")
            }
        }
    }
}

@Preview(name = "Light Mode", backgroundColor = 0xFFC2C2C2)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyBottomBar() {
    MyInvoiceTheme {
        MyBottomBar(
            enableDelete = true,
            enableSave = true,
            {}, {}, {}
        )
    }
}
