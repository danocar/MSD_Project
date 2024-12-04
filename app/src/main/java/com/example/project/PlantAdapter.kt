package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

class PlantAdapter(
    private val plants: MutableList<Plant>,
    private val onAddClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.imgPlant)
        val plantName: TextView = itemView.findViewById(R.id.tvPlantName)
        val plantLatinName: TextView = itemView.findViewById(R.id.tvLatinName)
        val btnAdd: ImageButton = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.plantName.text = plant.name
        holder.plantLatinName.text = plant.latinName
        holder.plantImage.setImageResource(plant.imageResId)

        // Handle the + button click
        holder.btnAdd.setOnClickListener {
            onAddClick(plant)
        }
    }

    override fun getItemCount(): Int = plants.size

    fun updatePlants(newPlants: List<Plant>) {
        plants.clear()
        plants.addAll(newPlants)
        Log.d("PlantAdapter", "Updated plants: ${plants.size}")
        notifyDataSetChanged()
    }
}
