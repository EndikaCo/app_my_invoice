package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun ActionButtons(
    enabled: Boolean,
    onAcceptClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        DeleteButton (
            onAcceptClick,
        )
        Spacer(modifier = Modifier.width(8.dp))
        CancelButton(onCancelClick)
        Spacer(modifier = Modifier.width(8.dp))
        AcceptButton(true, onAcceptClick)
    }
}

@Composable
fun AcceptButton(enabled: Boolean, onButtonClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { onButtonClick() },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0xFF5EA752),
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = Color.Transparent
            ),
            enabled = enabled,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.size(48.dp),
        ) {}

        Icon(imageVector = Icons.Default.Check, contentDescription = "", Modifier.size(30.dp))
    }
}

@Composable
fun CancelButton(onButtonClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { onButtonClick() },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0xFF6B6B6B),
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = Color.Transparent
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.size(48.dp)
        ) {}

        Icon(imageVector = Icons.Default.Clear, contentDescription = "", Modifier.size(30.dp))
    }
}

@Composable
fun DeleteButton(onButtonClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { onButtonClick() },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0xFFAC3636),
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = Color.Transparent
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.size(48.dp)
        ) {}

        Icon(imageVector = Icons.Default.Delete, contentDescription = "", Modifier.size(30.dp))
    }
}

@Preview(name = "ENABLED - Light Mode")
@Preview(name = "ENABLED - Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActionButtonsPreviewEnabled() {
    MyInvoiceTheme {
        ActionButtons(
            enabled = true,
            onAcceptClick = {},
            onCancelClick = {}
        )
    }
}

@Preview(name = "Light Mode", backgroundColor = 0xFFC2C2C2)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActionButtonsPreviewDisabled() {
    MyInvoiceTheme {
        ActionButtons(
            enabled = false,
            onAcceptClick = {},
            onCancelClick = {}
        )
    }
}