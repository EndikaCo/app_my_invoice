package com.endcodev.myinvoice.presentation.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.presentation.theme.MyInvoiceTheme

@Composable
fun MySearchBar(
    searchText: String,
    onTextChanged: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = { onTextChanged(it) },
        leadingIcon = {
            Icon(Icons.Filled.Search, stringResource(R.string.search_bar_icon))
        },
        trailingIcon = {
            Row {
                Icon(
                    painterResource(id = R.drawable.filter_24),
                    contentDescription = stringResource(R.string.serach_bar_filter),
                    Modifier.clickable { onFilterClick() })
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(R.string.seach_bar_clean_content),
                    Modifier.clickable { onTextChanged("") })
                Spacer(modifier = Modifier.size(8.dp))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.search_bar_placeholder)) }
    )
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    MyInvoiceTheme {
        MySearchBar(searchText = "Search", onTextChanged = {}, onFilterClick = {})
    }
}