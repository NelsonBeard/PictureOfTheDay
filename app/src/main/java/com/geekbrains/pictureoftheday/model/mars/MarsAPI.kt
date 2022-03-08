package com.geekbrains.pictureoftheday.model.mars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsAPI {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMars(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String
    ): Call<MarsServerResponseData>
}