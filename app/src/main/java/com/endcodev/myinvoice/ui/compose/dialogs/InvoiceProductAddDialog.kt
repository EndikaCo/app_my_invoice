package com.endcodev.myinvoice.ui.compose.dialogs

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun InvoiceProductAddDialog(onDialogAccept: () -> Unit, onDialogCancel: () -> Unit) {

    Dialog(
        onDismissRequest = { onDialogCancel() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .height(IntrinsicSize.Min)
        )
        {}
    }
}

@Preview
@Composable
fun InvoiceProductAddDialogPreview() {
    MyInvoiceTheme {
        InvoiceProductAddDialog(onDialogAccept = {}, onDialogCancel = {})
    }
}