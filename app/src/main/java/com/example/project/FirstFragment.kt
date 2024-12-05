package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

class FirstFragment : Fragment() {
    private lateinit var plantAdapter: PlantAdapter
    private val plantsViewModel: PlantsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvMyPlants)
        plantAdapter = PlantAdapter(mutableListOf()) { plant ->
            plantsViewModel.removePlantFromFavourites(plant)
            Toast.makeText(context, "${plant.name} removed from My Plants!", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = plantAdapter

        // Observe favourite plants and update the adapter when data changes
        plantsViewModel.favouritePlants.observe(viewLifecycleOwner, Observer { plants ->
            plantAdapter.updatePlants(plants)
        })

        return view
    }
}
