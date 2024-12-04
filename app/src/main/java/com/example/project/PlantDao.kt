package com.example.project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: Plant)

    @Insert
    suspend fun insertAll(plants: List<Plant>)

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>
}
