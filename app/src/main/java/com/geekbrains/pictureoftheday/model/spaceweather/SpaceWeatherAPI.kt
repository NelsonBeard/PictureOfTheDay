package com.geekbrains.pictureoftheday.model.spaceweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceWeatherAPI {
    @GET("DONKI/GST")
    fun getSpaceWeather(
        @Query("startDate") start_date: String,
        @Query("endDate") end_date: String,
        @Query("api_key") apiKey: String
    ): Call<List<SpaceWeatherServerResponseData>>
}