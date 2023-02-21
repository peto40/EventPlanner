package com.eventplanner.database.remote.api

import com.eventplanner.database.remote.dto.WeatherModel
import com.eventplanner.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("data/2.5/weather?")
    fun getData(
        @Query("q") cityName: String,
        @Query("appid") key: String = Constants.API_KEY,
    ): Call<WeatherModel>

}