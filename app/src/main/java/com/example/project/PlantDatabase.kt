package com.example.project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Plant::class], version = 3, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        // Add migrations for schema changes
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the 'isFavourite' column to the plants table
                database.execSQL("ALTER TABLE plants ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): PlantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                )
                    .addMigrations(MIGRATION_2_3) // Add migration here
                    .fallbackToDestructiveMigration() // Optional: For testing, clears database if migration is missing
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
