package com.sosnowskydevelop.tripmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sosnowskydevelop.tripmanager.utilities.DATABASE_NAME

@Database(entities = [Trip::class, Refueling::class], version = 7)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun tripDao(): TripDao
    abstract fun refuelingDao(): RefuelingDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration().build()
        }
    }
}