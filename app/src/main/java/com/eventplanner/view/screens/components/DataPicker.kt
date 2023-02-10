package com.eventplanner.view.screens.components

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DataPicker(
    modifier: Modifier = Modifier,
    date: String
) {
    var mDate by remember {
        mutableStateOf(date)
    }
    val mContext = LocalContext.current

    val year: Int
    val month: Int
    val day: Int

    val mCalendar = Calendar.getInstance()

    year = mCalendar.get(Calendar.YEAR)
    month = mCalendar.get(Calendar.MONTH)
    day = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, year, month, day
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = mDate.toString()
        )

        Button(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                mDatePickerDialog.show()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "Set Date", color = Color.Black)
        }

    }
}



