package com.eventplanner.view.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
): @Composable Unit {
    val selected by remember { mutableStateOf(text) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected == "IsPassed", onClick = onClick)
        Text(
            text = "IsPassed",
            modifier = Modifier.clickable(onClick = onClick).padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(selected = selected == "IsMissed", onClick = onClick)
        Text(
            text = "IsMissed",
            modifier = Modifier.clickable(onClick = onClick).padding(start = 4.dp)
        )
    }
}
