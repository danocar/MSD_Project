package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import android.util.Log

// Fragment for displaying and managing the "Find Plants" page
class SecondFragment : Fragment() {
    private lateinit var plantAdapter: PlantAdapter
    private val plantsViewModel: PlantsViewModel by activityViewModels()
    private lateinit var plantDao: PlantDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        // Initialize plantDao from the database
        val database = PlantDatabase.getDatabase(requireContext())
        plantDao = database.plantDao()

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvFindPlants)
        plantAdapter = PlantAdapter(mutableListOf()) { plant ->
            // Add plant to favourites and notify the user
            plantsViewModel.addPlantToFavourites(plant)
            Toast.makeText(context, "${plant.name} added to My Plants!", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = plantAdapter

        // Load plants from the database
        loadPlantsFromDatabase()

        // Set up SearchView
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterPlants(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterPlants(newText.orEmpty())
                return true
            }
        })

        // Save hardcoded plants to the database if not already saved
        saveHardcodedPlantsToDatabase()

        return view
    }

    // Save a predefined list of plants to the database
    private fun saveHardcodedPlantsToDatabase() {
        lifecycleScope.launch {
            val existingPlants = plantDao.getAllPlants()

            if (existingPlants.isEmpty()) {
                val hardcodedPlants = listOf(
                    Plant(name = "Aloe Vera", latinName = "Aloe vera", imageResId = R.drawable.aloe_vera, waterFrequency = 5),
                    Plant(name = "Boston Fern", latinName = "Nephrolepis exaltata", imageResId = R.drawable.boston_fern, waterFrequency = 3),
                    Plant(name = "Cast Iron Plant", latinName = "Aspidistra elatior", imageResId = R.drawable.cast_iron_plant, waterFrequency = 10),
                    Plant(name = "Chinese Evergreen", latinName = "Aglaonema spp.", imageResId = R.drawable.chinese_evergreen, waterFrequency = 7),
                    Plant(name = "Dracaena", latinName = "Dracaena marginata", imageResId = R.drawable.dracaena, waterFrequency = 14),
                    Plant(name = "Fiddle Leaf Fig", latinName = "Ficus lyrata", imageResId = R.drawable.fiddle_leaf_fig, waterFrequency = 6),
                    Plant(name = "Jade Plant", latinName = "Crassula ovata", imageResId = R.drawable.jade_plant, waterFrequency = 21),
                    Plant(name = "Monstera", latinName = "Monstera deliciosa", imageResId = R.drawable.monstera, waterFrequency = 8),
                    Plant(name = "Parlor Palm", latinName = "Chamaedorea elegans", imageResId = R.drawable.parlor_palm, waterFrequency = 15),
                    Plant(name = "Peace Lily", latinName = "Spathiphyllum", imageResId = R.drawable.peace_lily, waterFrequency = 5),
                    Plant(name = "Philodendron", latinName = "Philodendron spp.", imageResId = R.drawable.philodendron, waterFrequency = 7),
                    Plant(name = "Pothos", latinName = "Epipremnum aureum", imageResId = R.drawable.pothos, waterFrequency = 10),
                    Plant(name = "Rubber Plant", latinName = "Ficus elastica", imageResId = R.drawable.rubber_plant, waterFrequency = 14),
                    Plant(name = "Snake Plant", latinName = "Sansevieria trifasciata", imageResId = R.drawable.snake_plant, waterFrequency = 21),
                    Plant(name = "Spider Plant", latinName = "Chlorophytum comosum", imageResId = R.drawable.spider_plant, waterFrequency = 9),
                    Plant(name = "ZZ Plant", latinName = "Zamioculcas zamiifolia", imageResId = R.drawable.zz_plant, waterFrequency = 30)
                )

                plantDao.insertAll(hardcodedPlants)
                Log.d("SecondFragment", "Hardcoded plants inserted into database")
            }
        }
    }

    // Load plants from the database and update the adapter
    private fun loadPlantsFromDatabase() {
        lifecycleScope.launch {
            val plants = plantDao.getAllPlants()
            Log.d("SecondFragment", "Loaded plants: ${plants.size}")
            plantAdapter.updatePlants(plants)
        }
    }

    // Filter plants based on the search query
    private fun filterPlants(query: String) {
        lifecycleScope.launch {
            val filteredPlants = plantDao.getAllPlants().filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.latinName.contains(query, ignoreCase = true)
            }
            plantAdapter.updatePlants(filteredPlants)
        }
    }
}
