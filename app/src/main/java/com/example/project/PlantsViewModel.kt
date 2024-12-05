package com.example.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    // Add plant to favourites (update 'isFavourite' to true)
    fun addPlantToFavourites(plant: Plant) {
        viewModelScope.launch {
            // Update the existing plant, setting isFavourite to true
            val updatedPlant = plant.copy(isFavourite = true)
            plantDao.updatePlant(updatedPlant)
        }
    }

    // Remove plant from the database (from My Plants)
    fun removePlantFromFavourites(plant: Plant) {
        viewModelScope.launch {
            plantDao.deletePlant(plant)  // This deletes the plant from the database
        }
    }

    // Retrieve favourite plants (LiveData for observing)
    val favouritePlants: LiveData<List<Plant>> = plantDao.getFavouritePlants()

}
