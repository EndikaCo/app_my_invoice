package com.endcodev.myinvoice.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.myinvoice.ui.theme.MyInvoiceTheme

@Composable
fun AcceptCancelButtons(
    enabled: Boolean,
    onAcceptClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        RectangleButton("CANCEL", Modifier.weight(1F), true, onCancelClick)
        Spacer(modifier = Modifier.width(25.dp))
        RectangleButton("ACCEPT", Modifier.weight(1F), enabled, onAcceptClick)
    }
}

@Composable
fun RectangleButton(text: String, modifier: Modifier, enabled: Boolean, onButtonClick: () -> Unit) {
    Button(
        onClick = { onButtonClick() },
        modifier = modifier.height(50.dp),
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Text(text = text, fontSize = 15.sp)
    }
}


@Preview(name = "ENABLED - Light Mode")
@Preview(name = "ENABLED - Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAcceptCancelButtons() {
    MyInvoiceTheme {
        AcceptCancelButtons(
            enabled = true,
            onAcceptClick = {},
            onCancelClick = {}
        )
    }
}

@Preview(name = "Light Mode", backgroundColor = 0xFFC2C2C2)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAcceptCancelButtons2() {
    MyInvoiceTheme {
        AcceptCancelButtons(
            enabled = false,
            onAcceptClick = {},
            onCancelClick = {}
        )
    }
}