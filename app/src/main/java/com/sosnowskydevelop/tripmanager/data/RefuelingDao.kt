package com.sosnowskydevelop.tripmanager.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RefuelingDao {
    @Query("SELECT * FROM refueling WHERE tripId = :tripId")
    fun findRefuelingListByTripId(tripId: Long): LiveData<List<Refueling>>

    @Query("SELECT * FROM refueling WHERE refuelingId = :refuelingId")
    fun findRefuelingById(refuelingId: Long): LiveData<Refueling>

    @Insert
    suspend fun insertRefueling(refueling: Refueling)

    @Update
    suspend fun updateRefueling(refueling: Refueling)

    @Delete
    suspend fun deleteRefueling(refueling: Refueling)
}