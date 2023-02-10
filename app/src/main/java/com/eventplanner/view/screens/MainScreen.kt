package com.eventplanner.view.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.NavController
import com.eventplanner.utils.defList
import com.eventplanner.view.screens.components.DisplayEventCard
import com.eventplanner.viewmodel.SharedViewModel
import com.eventplanner.utils.NavDestination.AddUpdateEventScreen

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: SharedViewModel,
) {
    val state = viewModel.allEventLiveData.observeAsState(defList)

    var fabHeight by remember {
        mutableStateOf(0)
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.onGloballyPositioned {
                    fabHeight = it.size.height
                },
                shape = CircleShape,
                onClick = {
                          navController.navigate(
                              route = AddUpdateEventScreen.destination
                          )
                },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {

        LazyColumn {

            itemsIndexed(state.value) { _, event ->
                DisplayEventCard(
                    currentEvent = event,
                    navController = navController,
                    onStatusClick = {
                        event.isPassed = event.isPassed.not()
                    }
                )
            }
        }
    }
}
