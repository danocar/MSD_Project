package com.example.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlantsViewModel(application: Application) : AndroidViewModel(application) {

    private val plantDao = PlantDatabase.getDatabase(application).plantDao()

    // Add a new plant to the database
    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            plantDao.insertPlant(plant)
        }
    }

    // Retrieve all plants from the database
    fun getAllPlants(callback: (List<Plant>) -> Unit) {
        viewModelScope.launch {
            val plants = plantDao.getAllPlants()
            callback(plants)
        }
    }
}

