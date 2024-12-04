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

class SecondFragment : Fragment() {
    private lateinit var plantAdapter: PlantAdapter
    private val plantsViewModel: PlantsViewModel by activityViewModels()
    private lateinit var plantDao: PlantDao



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        // Initialise plantDao from the database
        val database = PlantDatabase.getDatabase(requireContext())
        plantDao = database.plantDao()

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvFindPlants)
        plantAdapter = PlantAdapter(mutableListOf()) { plant ->
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

        saveHardcodedPlantsToDatabase()


        return view
    }


    private fun saveHardcodedPlantsToDatabase() {
        lifecycleScope.launch {
            val existingPlants = plantDao.getAllPlants()

            if (existingPlants.isEmpty()) {
                val hardcodedPlants = listOf(
                    Plant(name = "Aloe Vera", latinName = "Aloe vera", imageResId = R.drawable.aloe_vera),
                    Plant(name = "Boston Fern", latinName = "Nephrolepis exaltata", imageResId = R.drawable.boston_fern),
                    Plant(name = "Cast Iron Plant", latinName = "Aspidistra elatior", imageResId = R.drawable.cast_iron_plant),
                    Plant(name = "Chinese Evergreen", latinName = "Aglaonema spp.", imageResId = R.drawable.chinese_evergreen),
                    Plant(name = "Dracaena", latinName = "Dracaena marginata", imageResId = R.drawable.dracaena),
                    Plant(name = "Fiddle Leaf Fig", latinName = "Ficus lyrata", imageResId = R.drawable.fiddle_leaf_fig),
                    Plant(name = "Jade Plant", latinName = "Crassula ovata", imageResId = R.drawable.jade_plant),
                    Plant(name = "Monstera", latinName = "Monstera deliciosa", imageResId = R.drawable.monstera),
                    Plant(name = "Parlor Palm", latinName = "Chamaedorea elegans", imageResId = R.drawable.parlor_palm),
                    Plant(name = "Peace Lily", latinName = "Spathiphyllum", imageResId = R.drawable.peace_lily),
                    Plant(name = "Philodendron", latinName = "Philodendron spp.", imageResId = R.drawable.philodendron),
                    Plant(name = "Pothos", latinName = "Epipremnum aureum", imageResId = R.drawable.pothos),
                    Plant(name = "Rubber Plant", latinName = "Ficus elastica", imageResId = R.drawable.rubber_plant),
                    Plant(name = "Snake Plant", latinName = "Sansevieria trifasciata", imageResId = R.drawable.snake_plant),
                    Plant(name = "Spider Plant", latinName = "Chlorophytum comosum", imageResId = R.drawable.spider_plant),
                    Plant(name = "ZZ Plant", latinName = "Zamioculcas zamiifolia", imageResId = R.drawable.zz_plant)
                )
                plantDao.insertAll(hardcodedPlants)
                Log.d("SecondFragment", "Hardcoded plants inserted into database")
            }
        }
    }

    private fun loadPlantsFromDatabase() {
        lifecycleScope.launch {
            val plants = plantDao.getAllPlants()
            Log.d("SecondFragment", "Loaded plants: ${plants.size}")
            plantAdapter.updatePlants(plants)

        }
    }

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
