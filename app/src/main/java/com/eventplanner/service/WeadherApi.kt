package com.eventplanner.service
import com.eventplanner.model.models.WeatherModel
import com.eventplanner.utils.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?")
    fun getData(
       @Query("q") cityName: String,
       @Query("appid") key: String = API_KEY
    ): Call<WeatherModel>

}