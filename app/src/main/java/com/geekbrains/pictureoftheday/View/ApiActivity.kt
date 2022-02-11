package com.geekbrains.pictureoftheday.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.ViewModel.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_api.*


class ApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREF, Context.MODE_PRIVATE)

        when (sharedPreferences.getString(THEME, null)) {
            "ThemeBlue" -> setTheme(R.style.ThemeBlue)
            "ThemeGreen" -> setTheme(R.style.ThemeGreen)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)

        view_pager.setPageTransformer(true, ZoomOutPageTransformer())

        tab_layout.setupWithViewPager(view_pager)

        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_mars)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_system)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_meteorite)
    }

}
