package com.geekbrains.pictureoftheday.viewModel.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.pictureoftheday.BuildConfig
import com.geekbrains.pictureoftheday.model.PictureOfTheDayRetrofitImp
import com.geekbrains.pictureoftheday.model.mars.MarsData
import com.geekbrains.pictureoftheday.model.mars.serverResponseData.MarsServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImp: PictureOfTheDayRetrofitImp = PictureOfTheDayRetrofitImp()
) : ViewModel() {

    fun getData(): LiveData<MarsData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MarsData.Loading(null)

        val date = LocalDate.now().minusDays(5).toString()
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            MarsData.Error(Throwable("You need API key"))
        } else {
            retrofitImp.getRetrofitImp().getMars(date, apiKey).enqueue(object :
                Callback<MarsServerResponseData> {
                override fun onResponse(
                    call: Call<MarsServerResponseData>,
                    response: Response<MarsServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            MarsData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                MarsData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                MarsData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = MarsData.Error(t)
                }
            })
        }
    }
}