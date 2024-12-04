package com.example.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // This line ensures the id is auto-generated
    val name: String,
    val latinName: String,
    val imageResId: Int,
    val isFavourite: Boolean = false
)


