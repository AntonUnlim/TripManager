package com.sosnowskydevelop.tripmanager.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trip")
    fun findAllTrips(): LiveData<List<Trip>>

    @Query("SELECT * FROM trip WHERE tripId = :tripId")
    fun findTripById(tripId: Long): LiveData<Trip>

    @Insert
    suspend fun insertTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)
}