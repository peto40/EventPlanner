package com.eventplanner.domain.repositiry

import com.eventplanner.domain.model.EventModel

interface EventRepository {

    suspend fun addEventToDB(event: EventModel)

    suspend fun getAllEventList(): List<EventModel>

    suspend fun updateEvent(event: EventModel)

    suspend fun deleteEvent(event: EventModel)
}