package com.geekbrains.pictureoftheday.view.pod

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.geekbrains.pictureoftheday.model.pod.PODData
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.view.ApiActivity
import com.geekbrains.pictureoftheday.view.SettingsFragment
import com.geekbrains.pictureoftheday.view.notes.NotesFragment
import com.geekbrains.pictureoftheday.viewModel.pod.PODViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_mars_start.*
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.*
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.image_view
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.input_edit_text
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.input_layout
import kotlinx.android.synthetic.main.fragment_picture_of_the_day_start.*

class PODFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var viewModel: PODViewModel

    private var isExpanded = false
    private var isPictureExpanded = false

    companion object {
        fun newInstance() = PODFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture_of_the_day_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PODViewModel::class.java]
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setFAB()

        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        fab_layout.setOnClickListener {
            collapseFab()
            input_edit_text.clearFocus()
            (context?.getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
            expandPicture()
            }
    }

    private fun expandPicture(): Boolean {
        isPictureExpanded = !isPictureExpanded
        TransitionManager.beginDelayedTransition(
            pod_layout, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )
        image_view.scaleType =
            if (isPictureExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        return true
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setFAB() {
        setInitialState()

        fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    private fun setInitialState() {
        fab_api.apply {
            alpha = 0f
            isClickable = false
        }
        fab_settings.apply {
            alpha = 0f
            isClickable = false
        }
        fab_notes.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true
        ObjectAnimator.ofFloat(plus_imageview, "rotation", 0f, 135f).start()
        ObjectAnimator.ofFloat(fab_notes, "translationY", -120f).start()
        ObjectAnimator.ofFloat(fab_settings, "translationY", -300f).start()
        ObjectAnimator.ofFloat(fab_api, "translationY", -480f).start()

        fab_notes.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fab_notes.isClickable = true
                    fab_notes.setOnClickListener {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.add(R.id.container, NotesFragment())?.addToBackStack(null)
                            ?.commit()
                    }
                }
            })

        fab_settings.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fab_settings.isClickable = true
                    fab_settings.setOnClickListener {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.add(R.id.container, SettingsFragment())?.addToBackStack(null)
                            ?.commit()
                    }
                }
            })

        fab_api.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fab_api.isClickable = true
                    fab_api.setOnClickListener {
                        activity?.let { startActivity(Intent(it, ApiActivity::class.java)) }
                    }
                }
            })
    }

    private fun collapseFab() {
        if (isExpanded) {
            ObjectAnimator.ofFloat(plus_imageview, "rotation", 135f, 0f).start()
        }
        ObjectAnimator.ofFloat(fab_notes, "translationY", 0f).start()
        ObjectAnimator.ofFloat(fab_settings, "translationY", 0f).start()
        ObjectAnimator.ofFloat(fab_api, "translationY", 0f).start()

        fab_notes.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fab_notes.isClickable = false
                    fab_notes.setOnClickListener(null)
                }
            })
        fab_settings.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fab_settings.isClickable = false
                    fab_settings.setOnClickListener(null)
                }
            })
        fab_api.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fab_api.isClickable = false
                    fab_api.setOnClickListener(null)
                }
            })
        isExpanded = false
    }

    private fun renderData(data: PODData) {
        when (data) {
            is PODData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    image_view.load(url) {
                        lifecycle(this@PODFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }

                    bottom_sheet_description_header.text = serverResponseData.title
                    bottom_sheet_description.text = serverResponseData.explanation
                }
            }
            is PODData.Loading -> {
                //Nothing to do
            }
            is PODData.Error -> {
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