package com.geekbrains.pictureoftheday.model

import com.geekbrains.pictureoftheday.model.mars.serverResponseData.MarsServerResponseData
import com.geekbrains.pictureoftheday.model.meteorite.serverResponseData.MeteoriteServerResponseData
import com.geekbrains.pictureoftheday.model.pod.PODServerResponseData
import com.geekbrains.pictureoftheday.model.spaceweather.serverResponseData.SpaceWeatherServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPOD(@Query("api_key") apiKey: String): Call<PODServerResponseData>

    @GET("DONKI/GST")
    fun getSpaceWeather(
        @Query("startDate") start_date: String,
        @Query("endDate") end_date: String,
        @Query("api_key") apiKey: String
    ): Call<List<SpaceWeatherServerResponseData>>

    @GET("neo/rest/v1/feed")
    fun getMeteorite(
        @Query("START_DATE") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Call<MeteoriteServerResponseData>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMars(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String
    ): Call<MarsServerResponseData>
}
