package com.geekbrains.pictureoftheday.viewModel.meteorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.pictureoftheday.BuildConfig
import com.geekbrains.pictureoftheday.model.PictureOfTheDayRetrofitImp
import com.geekbrains.pictureoftheday.model.meteorite.MeteoriteData
import com.geekbrains.pictureoftheday.model.meteorite.serverResponseData.MeteoriteServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val date = "2022-03-10"

class MeteoriteViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MeteoriteData> = MutableLiveData(),
    private val retrofitImp: PictureOfTheDayRetrofitImp = PictureOfTheDayRetrofitImp()
) : ViewModel() {

    fun getData(): LiveData<MeteoriteData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MeteoriteData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            MeteoriteData.Error(Throwable("You need API key"))
        } else {
            retrofitImp.getRetrofitImp().getMeteorite(date, date, apiKey).enqueue(object :
                Callback<MeteoriteServerResponseData> {
                override fun onResponse(
                    call: Call<MeteoriteServerResponseData>,
                    response: Response<MeteoriteServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            MeteoriteData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                MeteoriteData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                MeteoriteData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MeteoriteServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = MeteoriteData.Error(t)
                }
            })
        }
    }
}