package com.eventplanner.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eventplanner.R

@Composable
fun DefaultRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,

) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = !selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.onSurface,
                unselectedColor = MaterialTheme.colors.onPrimary
            )
        )
        Text(
            text = stringResource(id = R.string.is_expect),
            modifier = Modifier
                .clickable(onClick = onClick)
        )
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.onSurface,
                unselectedColor = MaterialTheme.colors.onPrimary
            )
        )
        Text(
            text = stringResource(id = R.string.is_missed),
            modifier = Modifier
                .clickable(onClick = onClick)
        )
    }
}
