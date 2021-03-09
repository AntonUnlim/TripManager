package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.sosnowskydevelop.tripmanager.data.Refueling

class RefuelingListItemViewModel internal constructor(
    val refueling: Refueling
) : ViewModel() {

    val odometer: String
        get() {
            return refueling.odometer
        }

    val isToFull: String
        get() {
            return refueling.isToFullCapitalize
        }
}