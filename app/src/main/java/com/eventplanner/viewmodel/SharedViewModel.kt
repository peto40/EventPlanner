package com.eventplanner.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.eventplanner.MyApplication
import com.eventplanner.database.EventRepository
import com.eventplanner.di.RetrofitServiceInterface
import com.eventplanner.model.models.EventModel
import com.eventplanner.model.models.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    application: Application,
    private val repository: EventRepository
) : AndroidViewModel(application) {

    private val _allEventLiveData by lazy { MutableLiveData<MutableList<EventModel>>() }
    val allEventLiveData get() = _allEventLiveData

    private val _weatherLiveData by lazy { MutableLiveData<WeatherModel>() }
    val weatherLiveData get() = _weatherLiveData


    @Inject
    lateinit var mService: RetrofitServiceInterface


    init {
        (application as MyApplication).getRetrofitComponent().inject(this)

        getEventListFromDB()
    }
////        val eventDb = EventDb.getInstance(getApplication())
////        eventDao = eventDb.eventDao()
////        getEventListFromDB(eventDao)
//    }

    fun addItemListToDB(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _allEventLiveData.value?.add(event)
            repository.addEventInDB(event)
           // eventDao.addEvent(event)
        }
    }

    private fun getEventListFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
           // _allEventLiveData.postValue(eventDao.getAll())
            _allEventLiveData.postValue(repository.getAllEventList())
        }
    }

    fun updateEvent(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
//            eventDao.update(event)
//            getEventListFromDB()
            repository.updateEvent(event)
            getEventListFromDB()
        }
    }

    fun deleteEventFromDB(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
//            eventDao.delete(event)
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
        val call = mService.getData(cityName)

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













