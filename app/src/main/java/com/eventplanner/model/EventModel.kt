package com.eventplanner.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel


@kotlinx.parcelize.Parcelize
@Entity(tableName = "events")
data class EventModel(
    val date: String,
    val time: String,
    val weatherDescription: String,
    val eventName: String,
    val description: String?,
    val location: String,
    val icon: String,
    var isPassed: Boolean = false
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}


