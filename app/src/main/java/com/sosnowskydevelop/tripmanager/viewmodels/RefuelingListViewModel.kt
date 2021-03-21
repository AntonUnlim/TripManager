package com.sosnowskydevelop.tripmanager.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.utilities.LOG_TAG

class RefuelingListViewModel internal constructor(
    private val refuelingRepository: RefuelingRepository
) : ViewModel() {

//    lateinit var refuelingList: LiveData<List<Refueling>>
    var refuelingList: LiveData<List<Refueling>> = refuelingRepository.getRefuelingList(0)

    fun initTrip(tripId: Long) {
        refuelingList = refuelingRepository.getRefuelingList(tripId)
        Log.i(LOG_TAG, "initTripId $tripId")
    }

    val averageFuelConsumption: ObservableField<String> = ObservableField()

    fun updateAverageFuelConsumption(refuelingList: List<Refueling>) {
        when {
            refuelingList.isEmpty() -> {
                averageFuelConsumption.set("Заправок нет")
            }
            refuelingList.size == 1 -> {
                averageFuelConsumption.set("Недостаточно данных")
            }
            else -> {
                val refuelingListForCalculatingDistance: List<Refueling> =
                        refuelingList.dropWhile { !it.isToFull }.dropLastWhile { !it.isToFull }

                if (refuelingListForCalculatingDistance.size <= 1) {

                    averageFuelConsumption.set("Недостаточно данных")

                } else {

                    val distanceForCalculating =
                            refuelingListForCalculatingDistance[refuelingListForCalculatingDistance.size - 1]
                                    .odometer.toInt() - refuelingListForCalculatingDistance[0]
                                    .odometer.toInt()

                    val refuelingListForCalculatingLiters: List<Refueling> =
                            refuelingListForCalculatingDistance.drop(1)

                    val litersForCalculating: Double = if (refuelingListForCalculatingLiters.size == 1) {
                        refuelingListForCalculatingLiters[0].litersFilled.toDouble()
                    } else {
                        refuelingListForCalculatingLiters.sumOf { it.litersFilled.toDouble() }
                    }

                    averageFuelConsumption.set((litersForCalculating / distanceForCalculating * 100).toString())
                }
            }
        }
    }
}