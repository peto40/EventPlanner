package com.eventplanner.presentation.viewmodel

import com.eventplanner.domain.model.EventModel


sealed class EventState{
    data class AddEvent(val event: EventModel): EventState()
    data class EditEvent(val event: EventModel): EventState()
    data class ChangeEventStatus(val status: Boolean): EventState()
    data class DeleteEvent(val event: EventModel): EventState()
}