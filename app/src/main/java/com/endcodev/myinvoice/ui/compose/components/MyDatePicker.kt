package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    openDialog: (Boolean) -> Unit,
    state: DatePickerState,
    newDate: (DatePickerState) -> Unit
) {

    DatePickerDialog(
        modifier = Modifier.wrapContentWidth(),
        onDismissRequest = {
            openDialog(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog(false)
                    newDate(state)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog(false)
                }
            ) {
                Text("CANCEL")
            }
        },
        colors = DatePickerDefaults.colors()
    ) {
        // Pass the lambda to the DatePicker to handle the selected date
        DatePicker(
            state = state,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomersContentPreview() {
    MyInvoiceTheme {
        MyDatePicker(openDialog = {}, state = rememberDatePickerState(), newDate = {})
    }
}