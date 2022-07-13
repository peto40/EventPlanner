package com.eventplanner.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.eventplanner.model.models.EventModel
import javax.inject.Inject

class EventRepository @Inject constructor (private val eventDao: EventDao) {


    suspend fun addEventInDB(event: EventModel){
        eventDao.addEvent(event)
    }

    fun getAllEventList() = eventDao.getAll()

    suspend fun updateEvent(event: EventModel){
        eventDao.update(event)
    }

    suspend fun deleteEvent(event: EventModel){
        eventDao.delete(event)
    }
}