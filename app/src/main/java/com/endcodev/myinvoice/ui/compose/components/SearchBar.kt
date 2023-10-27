package com.endcodev.myinvoice.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSearchBar(searchText: String, valueChanged: (String) -> Unit, onCleanClick: () -> Unit) {
    TextField(
        value = searchText,
        onValueChange = { valueChanged(it) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "search") },
        trailingIcon = {
            Icon(
                Icons.Filled.Close,
                contentDescription = "clean",
                Modifier.clickable { onCleanClick() })
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") }
    )
}