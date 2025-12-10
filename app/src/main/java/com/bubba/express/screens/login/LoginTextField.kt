package com.bubba.express.screens.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bubba.express.ui.theme.CoffeeAccent
import com.bubba.express.ui.theme.CoffeeSecondary
import com.bubba.express.ui.theme.CreamBackground
import com.bubba.express.ui.theme.GrayDark
import com.bubba.express.ui.theme.GrayLight
import com.bubba.express.ui.theme.GrayMedium

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = CoffeeSecondary,
            unfocusedIndicatorColor = GrayLight,
            cursorColor = CoffeeAccent,
            focusedLabelColor = CoffeeSecondary,
            unfocusedLabelColor = GrayMedium,
            focusedTextColor = GrayDark,
            unfocusedTextColor = GrayDark,
            focusedContainerColor = CreamBackground,
            unfocusedContainerColor = CreamBackground
        )

    )

}
