package com.geekbrains.pictureoftheday

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.pictureoftheday.ui.picture.PictureOfTheDayFragment

private const val PREF = "pref"
private const val THEME = "theme"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREF, Context.MODE_PRIVATE)

        when (sharedPreferences.getString(THEME, null)) {
            "ThemeBlue" -> setTheme(R.style.ThemeBlue)
            "ThemeGreen" -> setTheme(R.style.ThemeGreen)
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