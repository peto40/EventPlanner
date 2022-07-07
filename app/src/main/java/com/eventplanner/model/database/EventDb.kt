package com.eventplanner.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eventplanner.model.models.EventModel


@Database(entities = [EventModel::class], version = 1)
abstract class EventDb : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        private var INSTANCE: EventDb? = null

        fun getInstance(context: Context): EventDb {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                EventDb::class.java,
                "event_database"
            )
                .build().also {
                    INSTANCE = it
                }
        }
    }

}