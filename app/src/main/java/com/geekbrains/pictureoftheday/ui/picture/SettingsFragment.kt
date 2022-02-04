package com.geekbrains.pictureoftheday.ui.picture

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.geekbrains.pictureoftheday.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val PREF = "pref"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chip_theme_1.setOnClickListener {
            setTheme(R.style.AppTheme)
        }
        chip_theme_2.setOnClickListener {
            setTheme(R.style.AppTheme2)
        }

        chip_dark_theme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        chip_light_theme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setTheme(themeId: Int) {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when (themeId){
            R.style.AppTheme ->  editor.putString("Theme", "AppTheme").apply()
            R.style.AppTheme2 ->  editor.putString("Theme", "AppTheme2").apply()
        }

        requireActivity().setTheme(themeId)
        requireActivity().recreate()
    }
}