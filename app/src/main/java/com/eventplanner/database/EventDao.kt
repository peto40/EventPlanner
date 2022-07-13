package com.eventplanner.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.eventplanner.model.models.EventModel
@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY id ASC")
    fun getAll(): MutableList<EventModel>

    @Insert(onConflict = REPLACE)
    suspend fun addEvent(event: EventModel)

    @Delete
    suspend fun delete(event: EventModel)

    @Update
    suspend fun update(event: EventModel)

}