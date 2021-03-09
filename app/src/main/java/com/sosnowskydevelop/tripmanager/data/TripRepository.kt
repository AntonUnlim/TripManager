package com.sosnowskydevelop.tripmanager.data

import androidx.lifecycle.LiveData

class TripRepository private constructor(private val tripDao: TripDao) {

    fun getTripList(): LiveData<List<Trip>> {
        return tripDao.findAllTrips()
    }

    fun getTrip(tripId: Long): LiveData<Trip> {
        return tripDao.findTripById(tripId = tripId)
    }

    suspend fun createTrip(trip: Trip) {
        tripDao.insertTrip(trip = trip)
    }

    suspend fun editTrip(trip: Trip) {
        tripDao.updateTrip(trip = trip)
    }

    suspend fun removeTrip(trip: Trip) {
        tripDao.deleteTrip(trip = trip)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TripRepository? = null

        fun getInstance(tripDao: TripDao) =
            instance ?: synchronized(this) {
                instance ?: TripRepository(tripDao = tripDao).also { instance = it }
            }
    }
}