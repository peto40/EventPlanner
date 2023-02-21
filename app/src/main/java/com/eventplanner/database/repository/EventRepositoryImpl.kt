package com.eventplanner.database.repository

import com.eventplanner.database.local.EventDao
import com.eventplanner.domain.model.EventModel
import com.eventplanner.domain.repositiry.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
private val eventDao: EventDao
): EventRepository {

    override suspend fun addEventToDB(event: EventModel) {
        eventDao.addEvent(event)
    }

    override suspend fun getAllEventList() = eventDao.getAll()

    override suspend fun updateEvent(event: EventModel) {
        eventDao.update(event)
    }

    override suspend fun deleteEvent(event: EventModel) {
        eventDao.delete(event)
    }
}