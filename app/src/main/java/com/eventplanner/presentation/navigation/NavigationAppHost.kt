package com.eventplanner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eventplanner.domain.model.EventModel
import com.eventplanner.presentation.screens.add_event.AddEventScreen
import com.eventplanner.presentation.screens.display_detail.EventDetailsScreen
import com.eventplanner.presentation.screens.main.MainScreen
import com.eventplanner.presentation.screens.update_event.UpdateEventScreen
import com.eventplanner.presentation.viewmodel.MainViewModel
import com.eventplanner.utils.Constants
import com.eventplanner.utils.Constants.CURRENT_EVENT_KEY
import com.eventplanner.utils.NavDestination

@Composable
fun NavigationAppHost(
    navController: NavHostController,
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = NavDestination.MainScreen.route,
    ) {
        composable(route = NavDestination.MainScreen.route) {
            MainScreen(
                navController = navController,
                viewModel = mainViewModel,
            )
        }
        composable(route = NavDestination.AddEventScreen.route) {
            AddEventScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(route = NavDestination.UpdateEventScreen.route) {
            navController.previousBackStackEntry?.arguments?.getParcelable<EventModel?>(
                    CURRENT_EVENT_KEY
                )?.let { currentEvent ->

                    UpdateEventScreen(currentEvent = currentEvent,
                        viewModel = mainViewModel,
                        onUpdateClicked = { updatedEvent ->
                            mainViewModel.updateEvent(updatedEvent)
                            navController.navigateUp()
                            it.arguments?.clear()

                        })
                }
        }
        composable(route = NavDestination.DisplayEventDetails.route) {
            navController.previousBackStackEntry?.arguments?.getParcelable<EventModel?>(
                Constants.DISPLAY_EVENT_KEY
            )?.let { currentEvent ->
                EventDetailsScreen(
                    currentEvent = currentEvent
                )
            }
        }
    }
}

