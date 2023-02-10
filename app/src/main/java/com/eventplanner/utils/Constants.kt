package com.eventplanner.utils

import com.eventplanner.model.EventModel

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/"

    const val API_KEY = "2e17da4388b74b5859e5c70b967139ee"
    
}

sealed class NavDestination(val destination: String) {
    object MainScreen: NavDestination("main")
    object AddUpdateEventScreen: NavDestination("add_update_event")
    object UpdateEventScreen : NavDestination("update_event")
    object DisplayEventDetails : NavDestination("event_details")
}

val defList = listOf(
    EventModel("", "", "", "", "", "", ""),
)
object Status {
    const val isExpect = "is_expect"
    const val missed = "missed"
}