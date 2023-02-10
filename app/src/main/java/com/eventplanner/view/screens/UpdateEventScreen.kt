package com.eventplanner.view.screens

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
import androidx.navigation.NavController
import com.eventplanner.R
import com.eventplanner.model.EventModel
import com.eventplanner.utils.NavDestination
import com.eventplanner.view.screens.components.DataPicker
import com.eventplanner.view.screens.components.DisplayReceivedWeather
import com.eventplanner.view.screens.components.InputTextField
import com.eventplanner.view.screens.components.TimePicker
import com.eventplanner.viewmodel.SharedViewModel

const val CURRENT_EVENT_KEY = "current_event_key"

@Composable
fun UpdateEventScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SharedViewModel,
    currentEvent: EventModel
) {
    val weatherModel = viewModel.weatherLiveData.observeAsState().value

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

        if (description != null) {
            InputTextField(
                hint = stringResource(R.string.lbl_description),
                textState = description,
                onValueChange = setDescription
            )
        }
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

        if (weatherModel != null) {
            DisplayReceivedWeather(weatherModel = weatherModel)
        }

        val event = weatherModel?.weather?.get(0)?.description?.let {
            EventModel(
                date = mDate,
                time = mTime,
                weatherDescription = it,
                eventName = eventName,
                description = description,
                location = location,
                icon = weatherModel.weather[0].icon,
                isPassed = false
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                navController.navigate(
                    route = NavDestination.MainScreen.destination,
                    )
                if (event != null) {
                    viewModel.updateEvent(event)
                }
            }
        ) {
            Text(
                text = stringResource(R.string.update_event),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}