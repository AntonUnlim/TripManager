package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import kotlinx.coroutines.launch

class RefuelingAddViewModel internal constructor(
    private val refuelingRepository: RefuelingRepository
) : ViewModel() {

    var odometer: ObservableField<String> = ObservableField()
    var litersFilled: ObservableField<String> = ObservableField()
    var pricePerLiter: ObservableField<String> = ObservableField()
    var isToFull: ObservableBoolean = ObservableBoolean()

    fun createRefueling() {
        viewModelScope.launch {
            refuelingRepository.createRefueling(Refueling(odometer.get().toString(),
                litersFilled.get().toString(), pricePerLiter.get().toString(), isToFull.get()))
        }
    }
}