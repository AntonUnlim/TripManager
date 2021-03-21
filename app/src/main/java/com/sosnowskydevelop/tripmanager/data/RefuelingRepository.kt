package com.sosnowskydevelop.tripmanager.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RefuelingRepository private constructor(
    private val refuelingDao: RefuelingDao
) {
    fun getRefuelingList(tripId: Long): LiveData<List<Refueling>> {
        return refuelingDao.findRefuelingListByTripId(tripId = tripId)
    }

    fun getRefueling(refuelingId: Long): LiveData<Refueling> {
        return refuelingDao.findRefuelingById(refuelingId = refuelingId)
    }

    suspend fun createRefueling(refueling: Refueling) {
        refuelingDao.insertRefueling(refueling = refueling)
    }

    suspend fun editRefueling(refueling: Refueling) {
        refuelingDao.updateRefueling(refueling = refueling)
    }

    suspend fun removeRefueling(refueling: Refueling) {
        refuelingDao.deleteRefueling(refueling = refueling)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: RefuelingRepository? = null

        fun getInstance(refuelingDao: RefuelingDao) =
            instance ?: synchronized(this) {
                instance ?: RefuelingRepository(refuelingDao = refuelingDao).also { instance = it }
            }
    }
}