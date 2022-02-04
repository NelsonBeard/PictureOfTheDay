package com.geekbrains.pictureoftheday

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.pictureoftheday.ui.picture.PictureOfTheDayFragment

private const val PREF = "pref"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val theme = sharedPreferences.getString("Theme", null)

        when (theme) {
            "AppTheme" -> setTheme(R.style.AppTheme)
            "AppTheme2" -> setTheme(R.style.AppTheme2)
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}