package com.geekbrains.pictureoftheday.Model.meteorite

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoriteAPI {

    @GET("neo/rest/v1/feed")
    fun getMeteorite(
        @Query("START_DATE") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Call<MeteoriteServerResponseData>
    }