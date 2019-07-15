package com.example.eventreminder.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventreminder.Api.service
import com.example.eventreminder.Constants
import com.example.eventreminder.Model.Events.Data
import com.example.eventreminder.Model.WeatherResponse.X
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModelActivity : ViewModel() {
    private val _response = MutableLiveData<List<Data>>()
    val response: LiveData<List<Data>>
        get() = _response

    private val _Weathrresponse = MutableLiveData<List<X>>()
    val WeatherResponse: LiveData<List<X>>
        get() = _Weathrresponse

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


/**
 * Here Getting the Result of Events ,associated with the weather Data
 * */
    fun getResult(id: String, newAccessToken: String) {
        coroutineScope.launch {
            val result = service.retrofitService.getEvents(id, newAccessToken)
            try {
                val results = result.await()
                if (results != null) {
                    Log.i("result ", "success   ${results.data.size}")
                    _response.value = results.data
                    println("Sorting by ascending : ")
                    val sortedList = results.data.sortedBy { it -> it.startTime }
                    sortedList.forEach { s -> println(s.startTime) }
                    _response.value = sortedList
                    getWeather()
                } else {
                    Log.i("result error", "result is $result")
                }


            } catch (e: Exception) {
                _response.value = null
                Log.i("event error", " error  due to ${e.message.toString()}")
            }
        }


    }


    /**
     * Here Getting the Result of Weather Data , passing it to LiveData of weather Response
     *
     *  * @TODO: should pass City from Events to the url of weather .. ..
     * */

    fun getWeather() {
        coroutineScope.launch {
            val result = service.retroftService2.getWeatherData("Egypt", Constants.APP_ID)
            try {
                val results = result.await()
                if (results != null) {
                    Log.i("weather ", "success   ${results.list.size}")
                    _Weathrresponse.value = results.list
                } else {
                    Log.i("weather error", "result is $result")
                }
            } catch (e: Exception) {
                _Weathrresponse.value = null
                Log.i("Weather error", " error  due to ${e.message.toString()}")
            }
        }
    }


}


