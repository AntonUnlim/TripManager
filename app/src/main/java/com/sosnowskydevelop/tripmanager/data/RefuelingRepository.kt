package com.sosnowskydevelop.tripmanager.data

class RefuelingRepository private constructor(
    private val refuelingDao: RefuelingDao
) {
    fun getRefuelingList() = refuelingDao.getRefuelingList()

    fun getRefueling(refuelingId: Long) = refuelingDao.getRefueling(refuelingId)

    suspend fun createRefueling(refueling: Refueling) {
        refuelingDao.insertRefueling(refueling)
    }

    suspend fun removeRefueling(refueling: Refueling) {
        refuelingDao.deleteRefueling(refueling)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: RefuelingRepository? = null

        fun getInstance(refuelingDao: RefuelingDao) =
            instance ?: synchronized(this) {
                instance ?: RefuelingRepository(refuelingDao).also { instance = it }
            }
    }
}