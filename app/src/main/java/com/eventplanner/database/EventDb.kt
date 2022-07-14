package com.eventplanner.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eventplanner.model.EventModel


@Database(entities = [EventModel::class], version = 1)
abstract class EventDb : RoomDatabase() {

    abstract fun eventDao(): EventDao
}

