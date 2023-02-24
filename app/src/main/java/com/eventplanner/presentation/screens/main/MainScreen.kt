package com.eventplanner.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.eventplanner.presentation.navigation.navigate
import com.eventplanner.presentation.screens.components.DisplayEventCard
import com.eventplanner.presentation.viewmodel.MainViewModel
import com.eventplanner.utils.Constants.CURRENT_EVENT_KEY
import com.eventplanner.utils.NavDestination
import com.eventplanner.utils.NavDestination.AddEventScreen

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val state by viewModel.allEventLiveData.observeAsState(listOf())
    var fabHeight by remember { mutableStateOf(0) }

    Scaffold(
        backgroundColor = Color.LightGray,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.onGloballyPositioned {
                    fabHeight = it.size.height
                },
                shape = CircleShape,
                onClick = {
                    navController.navigate(
                        route = AddEventScreen.route
                    )
                },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValue ->

        LazyColumn {
            itemsIndexed(state) { id, event ->
                var statusState by remember { mutableStateOf(value = event.isPassed) }

                DisplayEventCard(
                    modifier = Modifier.padding(paddingValues = paddingValue),
                    currentEvent = event,
                    navController = navController,
                    onChecked = statusState,
                    onStatusClick = { currentEvent ->
                        statusState = statusState.not()
                        viewModel.updateEvent(event = currentEvent.copy(isPassed = statusState))
                    },
                    onUpdateClicked = { currentEvent ->
                        navController.navigate(
                            route = NavDestination.UpdateEventScreen.route,
                            params = bundleOf(CURRENT_EVENT_KEY to currentEvent.copy(id = id + 1)),
                        )
                    }
                )
            }
        }
    }
}
