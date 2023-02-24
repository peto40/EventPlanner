package com.eventplanner.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eventplanner.R
import com.eventplanner.domain.model.EventModel

import com.eventplanner.utils.NavDestination

@Composable
fun DisplayEventCard(
    modifier: Modifier = Modifier,
    currentEvent: EventModel,
    navController: NavController,
    onChecked: Boolean,
    onStatusClick: (EventModel) -> Unit,
    onUpdateClicked: (EventModel) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(8))
            .background(MaterialTheme.colors.onBackground)
            .clickable {
                navController.navigate(
                    route = NavDestination.DisplayEventDetails.route,
//                    param = mapOf(currentEvent.id, currentEvent).map { Pair<Int?, EventModel?>(first = ) }
                )
                Log.d("currentEvent", currentEvent.id.toString())
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarViewMonth,
                    contentDescription = "calendar",
                    modifier = Modifier.padding(6.dp),
                    tint = MaterialTheme.colors.onSurface
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = currentEvent.date,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Icon(
                    imageVector = Icons.Default.Timelapse,
                    contentDescription = "time",
                    modifier = Modifier.padding(start = 12.dp),
                    tint = MaterialTheme.colors.onSurface
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = currentEvent.time,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            IconButton(
                onClick = { onUpdateClicked(currentEvent) },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp)
            ) {
                Icon(
                    tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Default.Edit,
                    contentDescription = "event_changes"
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.event_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = currentEvent.eventName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.lbl_description),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = currentEvent.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.location),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "location",
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(18.dp)
            )

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = currentEvent.location,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.the_weather_at_that_time),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = currentEvent.weatherDescription,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            NetworkIcon(
                modifier = Modifier.padding(start = 16.dp),
                url = currentEvent.icon
            )
            Image(
                painter = painterResource(id = R.drawable.ic_add_icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )

        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.temperature),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = currentEvent.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                modifier = modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.status),
                fontSize = 16.sp
            )
            val status = if (onChecked) "Missed" else "Expect"
            Text(
                modifier = modifier.padding(start = 6.dp), text = status, fontSize = 16.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultRadioButton(
                    selected = onChecked,
                    onClick = { onStatusClick(currentEvent) }
                )

            }

        }

    }
}


