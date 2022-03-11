package com.geekbrains.pictureoftheday.viewModel.meteorite


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.pictureoftheday.R
import com.geekbrains.pictureoftheday.R.color.*
import com.geekbrains.pictureoftheday.model.meteorite.serverResponseData.Meteorite

class MeteoriteRecyclerAdapter(private val meteorites: List<Meteorite>) :
    RecyclerView.Adapter<MeteoriteRecyclerAdapter.MeteoriteViewHolder>() {
    class MeteoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.meteor_name)
        val id: TextView = itemView.findViewById(R.id.meteor_id)
        val diameter: TextView = itemView.findViewById(R.id.meteor_diameter)
        val closeApproachDate: TextView = itemView.findViewById(R.id.meteor_close_approach_date)
        val relativeVelocity: TextView = itemView.findViewById(R.id.meteor_relative_velocity)
        val missDistance: TextView = itemView.findViewById(R.id.meteor_miss_distance)
        val hazardous: TextView = itemView.findViewById(R.id.meteor_hazardous)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeteoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.meteorite_recyclerview_item, parent, false)
        return MeteoriteViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MeteoriteViewHolder, position: Int) {
        holder.name.text = meteorites[position].name
        holder.id.text = meteorites[position].id
        holder.diameter.text = meteorites[position].estimated_diameter.meters.estimated_diameter_max.toString()
        holder.closeApproachDate.text = meteorites[position].close_approach_data[0].close_approach_date_full
        holder.relativeVelocity.text = meteorites[position].close_approach_data[0].relative_velocity.kilometers_per_second
        holder.missDistance.text = meteorites[position].close_approach_data[0].miss_distance.kilometers
        holder.hazardous.text = meteorites[position].hazardous.toString()

        if (meteorites[position].hazardous){
            holder.itemView.setBackgroundResource(meteorite_item_hazard_background)
        } else{
            holder.itemView.setBackgroundResource(meteorite_item_background)
        }
    }

    override fun getItemCount(): Int {
        return meteorites.size
    }
}