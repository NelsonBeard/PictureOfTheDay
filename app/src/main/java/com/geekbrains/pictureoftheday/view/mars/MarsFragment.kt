package com.geekbrains.pictureoftheday.view.mars

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.model.mars.MarsData
import com.geekbrains.pictureoftheday.viewModel.mars.MarsViewModel
import kotlinx.android.synthetic.main.fragment_mars_end.*
import kotlinx.android.synthetic.main.fragment_mars_start.*
import kotlinx.android.synthetic.main.fragment_mars_start.camera_name
import kotlinx.android.synthetic.main.fragment_mars_start.mars_date
import kotlinx.android.synthetic.main.fragment_mars_start.mars_hint
import kotlinx.android.synthetic.main.fragment_mars_start.mars_photos
import kotlinx.android.synthetic.main.fragment_mars_start.photo_id
import kotlinx.android.synthetic.main.fragment_mars_start.rover_name
import kotlin.random.Random

const val DURATION: Long = 120

class MarsFragment : Fragment() {

    private lateinit var viewModel: MarsViewModel
    private var show = false
    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MarsViewModel::class.java]
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

        mars_photos.setOnClickListener {
            if (!isExpanded) {
                if (show) hideComponents() else showComponents()
            }
        }
        mars_photos.setOnLongClickListener { expandPicture() }
    }

    private fun expandPicture(): Boolean {
        isExpanded = !isExpanded
        TransitionManager.beginDelayedTransition(
            constraint_container, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        mars_photos.scaleType =
            if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        return true
    }

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_mars_end)

        val transition = ChangeBounds()
        transition.duration = DURATION

        TransitionManager.beginDelayedTransition(constraint_container, transition)
        constraintSet.applyTo(constraint_container)
    }

    private fun hideComponents() {
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_mars_start)

        val transition = ChangeBounds()
        transition.duration = DURATION

        TransitionManager.beginDelayedTransition(constraint_container, transition)
        constraintSet.applyTo(constraint_container)
    }

    private fun renderData(data: MarsData) {

        val spannable =
            SpannableString("Hint:\n tap on picture to show description\n long press to zoom")

        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            7, 10,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            43, 53,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        mars_hint.text = spannable

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
