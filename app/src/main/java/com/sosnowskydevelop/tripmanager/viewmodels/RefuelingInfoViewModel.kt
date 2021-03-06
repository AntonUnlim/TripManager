package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_LOWER
import kotlinx.coroutines.launch

class RefuelingInfoViewModel internal constructor(
    private val refuelingRepository: RefuelingRepository
) : ViewModel() {
    lateinit var refueling: LiveData<Refueling>
    var refuelingToDelete: Refueling? = null
    var refuelingId: Long = 0

    val odometer: ObservableField<String> = ObservableField()
    val litersFilled: ObservableField<String> = ObservableField()
    val pricePerLiter: ObservableField<String> = ObservableField()
    val isToFull: ObservableField<String> = ObservableField()
    val parentTripID: ObservableField<Long> = ObservableField()

    fun initRefueling(refuelingId: Long) {
        this.refuelingId = refuelingId
        refueling = refuelingRepository.getRefueling(refuelingId)
    }

    fun removeRefueling() {
        viewModelScope.launch {

            val refueling = Refueling(
                    odometer.get().toString(),
                    litersFilled.get().toString(),
                    pricePerLiter.get().toString(),
                    getBooleanIsToFull(),
                    parentTripID.get()?:0
            )

            refueling.refuelingId = refuelingId

            refuelingRepository.removeRefueling(refueling)
        }
    }

    private fun getBooleanIsToFull(): Boolean {
        return isToFull.get() == TO_FULL_LOWER
    }
}