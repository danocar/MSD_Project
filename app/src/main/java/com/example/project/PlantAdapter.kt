package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

// Adapter for displaying a list of plants in a RecyclerView
class PlantAdapter(
    private val plants: MutableList<Plant>,
    private val onAddClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    // ViewHolder for individual plant items
    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.imgPlant)
        val plantName: TextView = itemView.findViewById(R.id.tvPlantName)
        val plantLatinName: TextView = itemView.findViewById(R.id.tvLatinName)
        val btnAdd: ImageButton = itemView.findViewById(R.id.btnAdd)
        val waterFrequency: TextView = itemView.findViewById(R.id.waterFrequency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        // Inflate the item layout for the plant
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.plantName.text = plant.name
        holder.plantLatinName.text = plant.latinName
        holder.plantImage.setImageResource(plant.imageResId)
        holder.waterFrequency.text = "💧 ${plant.waterFrequency} days"

        // Handle + button click
        holder.btnAdd.setOnClickListener {
            onAddClick(plant)
        }
    }

    // Return the total number of plants
    override fun getItemCount(): Int = plants.size

    fun updatePlants(newPlants: List<Plant>) {
        plants.clear() //
        plants.addAll(newPlants)
        Log.d("PlantAdapter", "Updated plants: ${plants.size}")
        notifyDataSetChanged()
    }
}
