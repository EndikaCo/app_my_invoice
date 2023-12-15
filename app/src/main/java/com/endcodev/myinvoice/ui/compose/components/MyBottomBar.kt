package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun MyBottomBar(
    enableDelete: Boolean,
    enableSave: Boolean,
    addItemVisible: Boolean,
    onDeleteClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onAcceptClick: () -> Unit,
) {

    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { onDeleteClick() },
                    enabled = enableSave,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Action delete")
                }
                Text("Delete", fontSize = 10.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { onAddItemClick() },
                    enabled = enableSave,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        Icons.Default.PlaylistAdd,
                        contentDescription = "Action add item",
                        modifier = Modifier.alpha(if (addItemVisible) 1f else 0f)
                    )
                }
                Text("Add Product", fontSize = 10.sp)

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { onAcceptClick() },
                    enabled = enableSave,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Action Save")
                }
                Text("Save", fontSize = 10.sp)
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
            addItemVisible = true,
            {}, {}, {}
        )
    }
}
