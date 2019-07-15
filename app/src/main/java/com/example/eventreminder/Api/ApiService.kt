package com.example.eventreminder.Api

import com.example.eventreminder.Model.Events.Events
import com.example.eventreminder.Model.WeatherResponse.WeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://graph.facebook.com"
private const val WEATHER_URL = "http://api.openweathermap.org"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



/**
 * Here Creating Singleton Retrofit instance to be used with each Api
 * */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(WEATHER_URL)
    .build()

interface ApiService {


    /**
     * Here Getting Events from Graph Facebook Api  Using GET Method
     * */
    @GET("/{user-id}/events")
    fun getEvents(@Path("user-id") userId: String, @Query("access_token") token: String):
            /*  The Coroutine Call Adapter allows us to return a Deferred, a Job with a result*/
            Deferred<Events>

    /**
     * Here Getting Weather Data from openweathermap.org Using GET Method
     * */


    @GET("/data/2.5/find")
    fun getWeatherData(@Query("q") country: String, @Query("appid") applicationID: String):
            /*  The Coroutine Call Adapter allows us to return a Deferred, a Job with a result*/
            Deferred<WeatherResponse>


}

object service {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
    val retroftService2: ApiService by lazy { retrofit2.create(ApiService::class.java) }
}