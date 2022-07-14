package com.eventplanner.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel


@Parcelize
@Entity(tableName = "events")
data class EventModel(
    val date: String,
    val time: String,
    val weatherDescription: String,
    val eventName: String,
    val description: String?,
    val location: String,
    val icon: String,
    val state: Boolean? = null
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}


