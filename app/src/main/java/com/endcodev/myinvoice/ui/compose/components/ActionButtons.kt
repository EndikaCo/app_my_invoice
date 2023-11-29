package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun ActionButtons(
    enabled: Boolean,
    onAcceptClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        DeleteButton(onDeleteClick)
        Spacer(modifier = Modifier.weight(1F))
        AcceptButton(enabled, onAcceptClick)
    }
}

@Composable
fun AcceptButton(enabled: Boolean, onButtonClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { onButtonClick() },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.onTertiary,
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = Color.Transparent
            ),
            enabled = enabled,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.height(56.dp)
        ) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "", Modifier.size(20.dp))
        }
    }
}

@Composable
fun DeleteButton(onButtonClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { onButtonClick() },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0xFFBD7676),
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = Color.Transparent
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.height(56.dp)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "", Modifier.size(20.dp))
        }
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
            onDeleteClick = {}
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
            onDeleteClick = {}
        )
    }
}