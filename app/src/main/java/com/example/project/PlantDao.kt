package com.example.project

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: Plant)

    @Insert
    suspend fun insertAll(plants: List<Plant>)

    @Update
    suspend fun updatePlant(plant: Plant) // This will update the existing plant

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>

    @Query("SELECT * FROM plants WHERE isFavourite = 1")
    fun getFavouritePlants(): LiveData<List<Plant>>
}

