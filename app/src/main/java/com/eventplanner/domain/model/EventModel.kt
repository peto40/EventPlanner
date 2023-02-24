package com.eventplanner.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "events")
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "weather_description")
    var weatherDescription: String,
    @ColumnInfo(name = "event_name")
    var eventName: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "weather_icon")
    var icon: String,
    @ColumnInfo(name = "is_passed")
    var isPassed: Boolean = false,
) : Parcelable

