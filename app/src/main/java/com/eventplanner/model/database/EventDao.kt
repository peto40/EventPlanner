package com.eventplanner.model.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.eventplanner.model.models.EventModel
@Dao
interface EventDao {



    @Query("SELECT * FROM events LIMIT 1")
    fun getFirsEvent(): EventModel

    @Query("SELECT * FROM events ORDER BY id ASC")
    fun getAll(): MutableList<EventModel>

    @Insert(onConflict = REPLACE)
    fun addEvent(event: EventModel)

    @Delete
    fun delete(event: EventModel)

    @Update
    fun update(event: EventModel)

}