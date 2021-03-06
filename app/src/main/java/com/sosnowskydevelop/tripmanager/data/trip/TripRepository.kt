package com.sosnowskydevelop.tripmanager.data.trip

class TripRepository private constructor(private val tripDao: TripDao) {
    fun getTripList() = tripDao.getAllTrips()
    fun getTrip(tripId: Long) = tripDao.getTrip(tripId)
    suspend fun createTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    suspend fun removeTrip(trip: Trip) {
        tripDao.deleteTrip(trip)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TripRepository? = null

        fun getInstance(tripDao: TripDao) =
            instance ?: synchronized(this) {
                instance ?: TripRepository(tripDao).also { instance = it }
            }
    }
}