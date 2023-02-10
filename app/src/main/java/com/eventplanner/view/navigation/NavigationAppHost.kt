package com.eventplanner.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eventplanner.model.EventModel
import com.eventplanner.utils.NavDestination
import com.eventplanner.view.screens.*
import com.eventplanner.view.screens.AddUpdateEventScreen
import com.eventplanner.view.screens.EventDetailsScreen
import com.eventplanner.view.screens.MainScreen
import com.eventplanner.viewmodel.SharedViewModel

@Composable
fun NavigationAppHost(
    navController: NavHostController,
    viewModel: SharedViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.MainScreen.destination
    ) {
        composable(route = NavDestination.MainScreen.destination) {
            MainScreen(navController = navController, viewModel)
        }
        composable(route = NavDestination.AddUpdateEventScreen.destination) {
            AddUpdateEventScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = NavDestination.UpdateEventScreen.destination) {
            navController.previousBackStackEntry?.arguments?.getParcelable<EventModel?>(
                CURRENT_EVENT_KEY
            ).let {
                if (it != null) {
                    UpdateEventScreen(
                        navController = navController,
                        currentEvent = it,
                        viewModel = viewModel
                    )
                }
            }
        }
        composable(route = NavDestination.DisplayEventDetails.destination) {
            navController.previousBackStackEntry?.arguments?.getParcelable<EventModel?>(
                CURRENT_EVENT_KEY
            ).let {
                if (it != null) {
                    EventDetailsScreen(
                        currentEvent = it
                    )
                }
            }
        }

    }
}

