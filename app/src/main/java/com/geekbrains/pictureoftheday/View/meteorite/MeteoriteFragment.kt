package com.geekbrains.pictureoftheday.View.meteorite

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.pictureoftheday.Model.meteorite.MeteoriteData
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.ViewModel.meteorite.MeteoriteRecyclerAdapter
import com.geekbrains.pictureoftheday.ViewModel.meteorite.MeteoriteViewModel

class MeteoriteFragment : Fragment() {
    private lateinit var viewModel: MeteoriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meteorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MeteoriteViewModel::class.java]
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })


    }

    private fun renderData(data: MeteoriteData) {

        when (data) {
            is MeteoriteData.Success -> {

                val serverResponseData = data.serverResponseData
                val listMeteorites = serverResponseData.near_earth_objects.meteorites
                val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView_meteorite)
                recyclerView.setHasFixedSize(true)

                recyclerView.layoutManager = LinearLayoutManager(context)
                if (listMeteorites.isNullOrEmpty()) {
                    toast("No data")
                } else {
                    recyclerView.adapter = MeteoriteRecyclerAdapter(listMeteorites)
                }
            }
            is MeteoriteData.Loading -> {
                //Nothing to do
            }
            is MeteoriteData.Error -> {
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
