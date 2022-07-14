package com.eventplanner.repositiry

import com.eventplanner.database.EventDao
import com.eventplanner.model.EventModel
import javax.inject.Inject

class EventRepository @Inject constructor (private val eventDao: EventDao) {

    suspend fun addEventInDB(event: EventModel){
        eventDao.addEvent(event)
    }

    suspend fun getAllEventList(): MutableList<EventModel> {
        return eventDao.getAll()
    }

    suspend fun updateEvent(event: EventModel){
        eventDao.update(event)
    }

    suspend fun deleteEvent(event: EventModel){
        eventDao.delete(event)
    }

 }