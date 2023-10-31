package com.endcodev.myinvoice.ui.compose.screens.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.endcodev.myinvoice.data.model.CountryModel
import com.endcodev.myinvoice.ui.compose.components.BottomButtons
import com.endcodev.myinvoice.ui.compose.components.CountrySelection
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun FiltersDialog(onDialogClick: (Int) -> Unit) {

    Dialog(
        onDismissRequest = { onDialogClick(0) }, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .height(IntrinsicSize.Min))
        {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Filter by",
                    modifier = Modifier
                        .padding(8.dp, 16.dp, 8.dp, 2.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(), fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                CountrySelection(Modifier)
            }
            Spacer( Modifier.height(16.dp))
            BottomButtons(enabled = true, onAcceptClick = { /*TODO*/ }) {
            }
        }
    }
}

@Preview
@Composable
fun FilterDialogPreview() {

    MyInvoiceTheme {
        FiltersDialog(onDialogClick = {})
    }
}

