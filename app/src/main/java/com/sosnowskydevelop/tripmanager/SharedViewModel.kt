package com.sosnowskydevelop.tripmanager

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.utilities.FIELD_EMPTY_ERROR
import com.sosnowskydevelop.tripmanager.utilities.ODOMETER_LESS_THAN_LAST_ERROR
import kotlinx.coroutines.launch

class SharedViewModel(
        private val refuelingRepository: RefuelingRepository//,
//        tripId: Long
) : ViewModel() {

//    val refuelingList: LiveData<List<Refueling>> =
//            refuelingRepository.getRefuelingList(tripId = tripId)

    /* From RefuelingAddViewModel */
    var isRefuelingCreated: Boolean = false

    val odometer: ObservableField<String> = ObservableField()
    val isOdometerError: ObservableBoolean = ObservableBoolean(false)
    val odometerErrorText: ObservableField<String> = ObservableField()

    val litersFilled: ObservableField<String> = ObservableField()
    val isLitersFilledError: ObservableBoolean = ObservableBoolean(false)
    val litersFilledErrorText: ObservableField<String> = ObservableField()

    val pricePerLiter: ObservableField<String> = ObservableField()

    val isToFull: ObservableBoolean = ObservableBoolean()

    private var lastRefueling: Refueling? = null
    private var parentTripID: Long = 0

    fun initLastRefueling(lastRefueling: Refueling?) {
        this.lastRefueling = lastRefueling
        odometer.set(lastRefueling?.odometer ?: "0")
    }

    fun initParentTrip(parentTripID: Long) {
        this.parentTripID = parentTripID
    }

    fun createRefueling() {
        val newRefuelingOdometer = odometer.get().toString()
        val newRefuelingLitersFilled = litersFilled.get().toString()
        val newRefuelingPricePerLiter = pricePerLiter.get().toString()
        val newRefuelingIsToFull = isToFull.get()

        when {
            isNewRefuelingOdometerEmpty(newRefuelingOdometer) -> {
                isOdometerError.set(true)
                odometerErrorText.set(FIELD_EMPTY_ERROR)
            }
            isNewRefuelingOdometerLessThanLast(newRefuelingOdometer) -> {
                isOdometerError.set(true)
                odometerErrorText.set(ODOMETER_LESS_THAN_LAST_ERROR)
            }
            isNewRefuelingLitersFilledEmpty(newRefuelingLitersFilled) -> {
                isLitersFilledError.set(true)
                litersFilledErrorText.set(FIELD_EMPTY_ERROR)
            }
            else -> {
                viewModelScope.launch {
                    refuelingRepository.createRefueling(refueling = Refueling(
                            tripId = parentTripID,
                            odometer = newRefuelingOdometer,
                            litersFilled = newRefuelingLitersFilled,
                            pricePerLiter = newRefuelingPricePerLiter,
                            isToFull = newRefuelingIsToFull
                    ))
                }
                isRefuelingCreated = true
            }
        }
    }

    private fun isNewRefuelingOdometerEmpty(newRefuelingOdometer: String): Boolean {
        return newRefuelingOdometer == "null" || newRefuelingOdometer.isEmpty()
    }

    private fun isNewRefuelingOdometerLessThanLast(newRefuelingOdometer: String): Boolean {
        val lastRefuelingOdometer = lastRefueling?.odometer?.toInt() ?: 0
        return newRefuelingOdometer.toInt() < lastRefuelingOdometer
    }

    private fun isNewRefuelingLitersFilledEmpty(newRefuelingLitersFilled: String): Boolean {
        return newRefuelingLitersFilled == "null" || newRefuelingLitersFilled.isEmpty()
    }

    /* From RefuelingListViewModel */
//    lateinit var refuelingList: LiveData<List<Refueling>>
    var refuelingList: LiveData<List<Refueling>> = refuelingRepository.getRefuelingList(tripId = 1)

    fun initTrip(tripId: Long) {
//        refuelingList = refuelingRepository.getRefuelingList(tripId = tripId)
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