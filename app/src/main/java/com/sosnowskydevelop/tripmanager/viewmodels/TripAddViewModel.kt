package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager.data.Trip
import com.sosnowskydevelop.tripmanager.data.TripRepository
import com.sosnowskydevelop.tripmanager.utilities.FIELD_EMPTY_ERROR
import kotlinx.coroutines.launch
import java.util.*

class TripAddViewModel internal constructor(
    private val tripRepository: TripRepository
) : ViewModel() {

    var isTripCreated: Boolean = false

    val name: ObservableField<String> = ObservableField()
    val isNameError: ObservableBoolean = ObservableBoolean(false)
    val nameErrorText: ObservableField<String> = ObservableField()

    fun createTrip() {
        val newTripName = name.get().toString()

        if (isNameEmpty(newTripName)) {
            isNameError.set(true)
            nameErrorText.set(FIELD_EMPTY_ERROR)
        } else {
            viewModelScope.launch {
                tripRepository.createTrip(trip = Trip(
                        name = newTripName,
                        beginDate = Calendar.getInstance().time
                ))
            }
            isTripCreated = true
        }
    }

    private fun isNameEmpty(newTripName: String): Boolean {
        return newTripName == "null" || newTripName.isEmpty()
    }
}