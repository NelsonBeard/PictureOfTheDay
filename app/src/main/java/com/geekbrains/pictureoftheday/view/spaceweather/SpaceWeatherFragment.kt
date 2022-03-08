package com.geekbrains.pictureoftheday.view.spaceweather

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.pictureoftheday.model.spaceweather.SpaceWeatherData
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.viewModel.spaceweather.SpaceWeatherViewModel
import kotlinx.android.synthetic.main.fragment_space_weather.*

class SpaceWeatherFragment : Fragment() {

    private lateinit var viewModel: SpaceWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_space_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SpaceWeatherViewModel::class.java]
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: SpaceWeatherData) {
        when (data) {

            is SpaceWeatherData.Success -> {
                val serverResponseData = data.serverResponseData
                val kpIndex = serverResponseData[0].allKpIndex[0].kpIndex

                when (kpIndex) {
                    0 -> space_weather_image.setImageResource(R.drawable.kp_0)
                    1 -> space_weather_image.setImageResource(R.drawable.kp_1)
                    2 -> space_weather_image.setImageResource(R.drawable.kp_2)
                    3 -> space_weather_image.setImageResource(R.drawable.kp_3)
                    4 -> space_weather_image.setImageResource(R.drawable.kp_4)
                    5 -> space_weather_image.setImageResource(R.drawable.kp_5)
                    6 -> space_weather_image.setImageResource(R.drawable.kp_6)
                    7 -> space_weather_image.setImageResource(R.drawable.kp_7)
                    8 -> space_weather_image.setImageResource(R.drawable.kp_8)
                    9 -> space_weather_image.setImageResource(R.drawable.kp_9)
                }

            }
            is SpaceWeatherData.Loading -> {
                //Nothing to do
            }
            is SpaceWeatherData.Error -> {
                if (data.error.message == "End of input at line 1 column 1 path \$") {
                    space_weather_image.setImageResource(R.drawable.no_storm)
                } else {
                    toast(data.error.message)
                }
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}
