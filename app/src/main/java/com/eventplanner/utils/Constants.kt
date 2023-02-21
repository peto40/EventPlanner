package com.eventplanner.utils

object Constants {

    // API keys
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "2e17da4388b74b5859e5c70b967139ee"

    // Event keys
    const val DISPLAY_EVENT_KEY = "display_event_key"
    const val CURRENT_EVENT_KEY = "current_event_key"
    const val UPDATE_EVENT_KEY = "current_event_key"


}

sealed class NavDestination(val route: String) {
    object MainScreen: NavDestination("main")
    object AddEventScreen: NavDestination("add_event")
    object UpdateEventScreen : NavDestination("update_event")
    object DisplayEventDetails : NavDestination("event_details")
}

