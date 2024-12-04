package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PlantAdapter(private val plants: MutableList<Plant>) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    // ViewHolder class to hold references to each item's views
    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.imgPlant)
        val plantName: TextView = itemView.findViewById(R.id.tvPlantName)
        val plantLatinName: TextView = itemView.findViewById(R.id.tvLatinName) // Match your XML ID
    }
    fun updatePlants(newPlants: List<Plant>) {
        plants.clear()
        plants.addAll(newPlants)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.plantName.text = plant.name
        holder.plantLatinName.text = plant.latinName.ifEmpty { "Unknown" }
        // Handle image loading (e.g., use a library like Glide for URLs)
        holder.plantImage.setImageResource(
            if (plant.imageResId != 0) plant.imageResId else R.drawable.monstera
        )
    }

    override fun getItemCount(): Int = plants.size
}
