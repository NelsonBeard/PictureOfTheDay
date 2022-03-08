package com.geekbrains.pictureoftheday.viewModel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.geekbrains.pictureoftheday.view.mars.MarsFragment
import com.geekbrains.pictureoftheday.view.meteorite.MeteoriteFragment
import com.geekbrains.pictureoftheday.view.spaceweather.SpaceWeatherFragment

private const val MARS_FRAGMENT = 0
private const val SPACE_WEATHER_FRAGMENT = 1
private const val METEORITE_FRAGMENT = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(MarsFragment(), SpaceWeatherFragment(), MeteoriteFragment())

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[MARS_FRAGMENT]
            1 -> fragments[SPACE_WEATHER_FRAGMENT]
            2 -> fragments[METEORITE_FRAGMENT]
            else -> fragments[MARS_FRAGMENT]
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Mars"
            1 -> "Weather"
            2 -> "Meteorite"
            else -> "Mars"
        }
    }
}
