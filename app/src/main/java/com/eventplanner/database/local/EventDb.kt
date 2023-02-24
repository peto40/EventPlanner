package com.eventplanner.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eventplanner.domain.model.EventModel


@Database(entities = [EventModel::class], version = 1)
abstract class EventDb : RoomDatabase() {

    abstract fun eventDao(): EventDao
}

