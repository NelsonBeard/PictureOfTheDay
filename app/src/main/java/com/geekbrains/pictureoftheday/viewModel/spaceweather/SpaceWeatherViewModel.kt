package com.geekbrains.pictureoftheday.viewModel.spaceweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.pictureoftheday.BuildConfig
import com.geekbrains.pictureoftheday.model.PictureOfTheDayRetrofitImp
import com.geekbrains.pictureoftheday.model.spaceweather.SpaceWeatherData
import com.geekbrains.pictureoftheday.model.spaceweather.serverResponseData.SpaceWeatherServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class SpaceWeatherViewModel(
    private val liveDataForViewToObserve: MutableLiveData<SpaceWeatherData> = MutableLiveData(),
    private val retrofitImp: PictureOfTheDayRetrofitImp = PictureOfTheDayRetrofitImp()
) : ViewModel() {

    fun getData(): LiveData<SpaceWeatherData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = SpaceWeatherData.Loading(null)

        val date = LocalDate.now().toString()
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            SpaceWeatherData.Error(Throwable("You need API key"))
        } else {
            retrofitImp.getRetrofitImp().getSpaceWeather(date,date, apiKey).enqueue(object :
                Callback<List<SpaceWeatherServerResponseData>> {
                override fun onResponse(
                    call: Call<List<SpaceWeatherServerResponseData>>,
                    response: Response<List<SpaceWeatherServerResponseData>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            SpaceWeatherData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                SpaceWeatherData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                SpaceWeatherData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<List<SpaceWeatherServerResponseData>>, t: Throwable) {
                    liveDataForViewToObserve.value = SpaceWeatherData.Error(t)
                }
            })
        }
    }
}