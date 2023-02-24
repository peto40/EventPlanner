package com.eventplanner.presentation.screens.update_event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eventplanner.R
import com.eventplanner.domain.model.EventModel
import com.eventplanner.presentation.screens.components.DataPicker
import com.eventplanner.presentation.screens.components.DisplayReceivedWeather
import com.eventplanner.presentation.screens.components.InputTextField
import com.eventplanner.presentation.screens.components.TimePicker
import com.eventplanner.presentation.viewmodel.MainViewModel


@Composable
fun UpdateEventScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    currentEvent: EventModel,
    onUpdateClicked: (EventModel) -> Unit,
) {
    val weatherModel = viewModel.weatherLiveData.observeAsState().value!!

    val (eventName, setName) = remember { mutableStateOf(currentEvent.eventName) }
    val (description, setDescription) = remember { mutableStateOf(currentEvent.description) }
    val (location, setLocation) = remember { mutableStateOf(currentEvent.location) }
    val mDate by remember { mutableStateOf(currentEvent.date) }
    val mTime by remember { mutableStateOf(currentEvent.time) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        InputTextField(
            hint = stringResource(R.string.event_name),
            textState = eventName,
            onValueChange = setName
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            hint = stringResource(R.string.lbl_description),
            textState = description,
            onValueChange = setDescription
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputTextField(
            hint = stringResource(R.string.location),
            textState = location,
            onValueChange = setLocation
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DataPicker(date = mDate)
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimePicker(time = mTime)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 16.dp),
            onClick = {
                viewModel.getWeatherData(location)
            }
        ) {
            Text(
                text = stringResource(R.string.get_weather),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        DisplayReceivedWeather(weatherModel = weatherModel)

        val event = EventModel(
            date = mDate,
            time = mTime,
            weatherDescription = weatherModel.weather[0].description,
            eventName = eventName,
            description = description,
            location = location,
            icon = weatherModel.weather[0].icon,
            id = currentEvent.id
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { onUpdateClicked(event) }
        ) {
            Text(
                text = stringResource(R.string.update_event),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}