package com.eventplanner.presentation.screens.add_event

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
import com.eventplanner.domain.model.EventModel
import com.eventplanner.presentation.screens.components.DataPicker
import com.eventplanner.presentation.screens.components.DisplayReceivedWeather
import com.eventplanner.presentation.screens.components.InputTextField
import com.eventplanner.presentation.screens.components.TimePicker
import com.eventplanner.presentation.viewmodel.MainViewModel
import com.eventplanner.utils.NavDestination


@Composable
fun AddEventScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainViewModel,
) {
    val weatherModel = viewModel.weatherLiveData.observeAsState().value

    val (eventName, setName) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }
    val (location, setLocation) = remember { mutableStateOf("") }

    val mDate by remember { mutableStateOf("01/01/2011") }
    val mTime by remember { mutableStateOf("12:03:45") }

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
                isPassed = false,
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                navController.navigate(route = NavDestination.MainScreen.route)
                if (event != null) {
                    viewModel.addEventToDB(event)
                }
            }
        ) {
            Text(
                text = stringResource(R.string.add_event),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}