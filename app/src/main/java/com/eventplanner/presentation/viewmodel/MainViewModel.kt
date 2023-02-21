package com.eventplanner.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventplanner.database.remote.api.RetrofitService
import com.eventplanner.domain.model.EventModel
import com.eventplanner.database.remote.dto.WeatherModel
import com.eventplanner.database.repository.EventRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EventRepositoryImpl,
    private val service: RetrofitService,
) : ViewModel() {

    private val _allEventLiveData by lazy { MutableLiveData<MutableList<EventModel>>() }
    val allEventLiveData get() = _allEventLiveData

    private val _weatherLiveData by lazy { MutableLiveData<WeatherModel>() }
    val weatherLiveData get() = _weatherLiveData

    init {
        getEventListFromDB()
    }
    fun addEventToDB(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEventToDB(event)
        }
        _allEventLiveData.value?.add(event)
    }

    private fun getEventListFromDB() {
        viewModelScope.launch(Dispatchers.IO)  {
            _allEventLiveData.postValue(repository.getAllEventList())
        }
    }

    fun updateEvent(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO)  {
            repository.updateEvent(event)
        }
        getEventListFromDB()
    }

    fun deleteEvent(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO)  {
            repository.deleteEvent(event)
            _allEventLiveData.value?.remove(event)
        }
    }

    fun getWeatherData(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataFromAPI(cityName)
        }
    }

    private fun getDataFromAPI(cityName: String) {
        val call = service.getData(cityName = cityName)
        call.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                weatherLiveData.value = response.body()
            }
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("response", "request failure ${t.stackTraceToString()}")
            }
        })
    }
}













