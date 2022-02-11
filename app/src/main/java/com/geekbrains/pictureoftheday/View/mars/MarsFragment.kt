package com.geekbrains.pictureoftheday.View.mars

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.geekbrains.pictureoftheday.Model.mars.MarsData
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.ViewModel.mars.MarsViewModel
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlin.random.Random

class MarsFragment : Fragment() {

    private lateinit var viewModel: MarsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MarsViewModel::class.java]
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: MarsData) {
        when (data) {
            is MarsData.Success -> {
                val serverResponseData = data.serverResponseData
                val listPhotos = serverResponseData.photos

                if (listPhotos.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {

                    val photo = listPhotos[Random.nextInt(0, 5)]
                    val photoUrl = photo.imgSrc
                    photo_id.text = "Photo id: " + photo.id.toString()
                    rover_name.text = "Rover: " + photo.rover.name
                    camera_name.text = "Camera: " + photo.camera.fullName
                    mars_date.text = "Date: " + photo.earthDate

                    mars_photos.load(photoUrl) {
                        lifecycle(this@MarsFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is MarsData.Loading -> {
                //Nothing to do
            }
            is MarsData.Error -> {
                toast(data.error.message)
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
