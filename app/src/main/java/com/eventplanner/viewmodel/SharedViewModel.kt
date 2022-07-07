package com.eventplanner.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.eventplanner.model.database.EventDao
import com.eventplanner.model.database.EventDb
import com.eventplanner.model.models.EventModel
import com.eventplanner.model.models.WeatherModel
import com.eventplanner.service.WeatherAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val _allEventLiveData by lazy { MutableLiveData<MutableList<EventModel>>() }
    val allEventLiveData get() = _allEventLiveData

    private val _weatherLiveData by lazy { MutableLiveData<WeatherModel>() }
    val weatherLiveData get() = _weatherLiveData

    private val weatherApiService = WeatherAPIService()

    private val eventDao: EventDao

    init {
        val eventDb = EventDb.getInstance(getApplication())
        eventDao = eventDb.eventDao()
        getEventListFromDB(eventDao)
    }

    fun addItemListToDB(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _allEventLiveData.value?.add(event)
            eventDao.addEvent(event)
        }
    }

    private fun getEventListFromDB(eventDao: EventDao) {
        viewModelScope.launch(Dispatchers.IO) {
            _allEventLiveData.postValue(eventDao.getAll())
        }
    }

    fun updateEvent(event: EventModel){
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.update(event)

        }
    }

    fun deleteEventFromDB(event: EventModel){
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.delete(event)
            _allEventLiveData.value?.remove(event)
        }
    }

    fun getWeatherData(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataFromAPI(cityName)
        }
    }


    private fun getDataFromAPI(cityName: String) {
        val call = weatherApiService.getDataService(cityName)

        call.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(
                call: Call<WeatherModel>,
                response: Response<WeatherModel>
            ) {
                weatherLiveData.value = response.body()
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("response", "request failure ${t.stackTraceToString()}")
            }
        })
    }

}