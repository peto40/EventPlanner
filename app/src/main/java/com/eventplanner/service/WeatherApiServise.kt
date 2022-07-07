package com.eventplanner.service

import com.eventplanner.model.models.WeatherModel
import com.eventplanner.utils.Constants.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName: String): Call<WeatherModel> {
        return api.getData(cityName)
    }

}