package com.example.project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Plant::class], version = 4, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        // Migration from version 1 to 2: Add waterFrequency column
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE plants ADD COLUMN waterFrequency INTEGER NOT NULL DEFAULT 0")
            }
        }

        // Migration from version 2 to 3: Add isFavourite column
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Apply both migrations
                    .fallbackToDestructiveMigration() // Optional: Clears database for unhandled migrations during testing
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
