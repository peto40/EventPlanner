package com.eventplanner.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun InputTextField(
    hint: String,
    modifier: Modifier = Modifier,
    textState: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = textState,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            disabledIndicatorColor = MaterialTheme.colors.primary,
            errorIndicatorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colors.primary
        ),

        label = { Text(text = hint) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp),
    )
}
