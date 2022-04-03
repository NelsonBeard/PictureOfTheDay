package com.geekbrains.pictureoftheday.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.pictureoftheday.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splash_image_view.animate().alpha(0f)
            .setInterpolator(AccelerateInterpolator(3f)).setDuration(1000)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    //nothing to do
                }

                override fun onAnimationCancel(animation: Animator?) {
                    //nothing to do
                }

                override fun onAnimationStart(animation: Animator?) {
                    //nothing to do
                }
            })
    }
}