package com.eventplanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eventplanner.model.models.EventModel


@Database(entities = [EventModel::class], version = 1)
abstract class EventDb : RoomDatabase() {

    abstract fun eventDao(): EventDao
}

