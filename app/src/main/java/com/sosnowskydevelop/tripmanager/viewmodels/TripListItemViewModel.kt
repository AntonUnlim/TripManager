package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.sosnowskydevelop.tripmanager.data.trip.Trip

class TripListItemViewModel internal constructor(
    val trip: Trip
) : ViewModel() {
    val name: String
        get() {
            return trip.name
        }
}