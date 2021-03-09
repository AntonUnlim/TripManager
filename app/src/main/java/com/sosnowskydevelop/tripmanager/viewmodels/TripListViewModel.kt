package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sosnowskydevelop.tripmanager.data.Trip
import com.sosnowskydevelop.tripmanager.data.TripRepository

class TripListViewModel internal constructor(
    tripRepository: TripRepository
) : ViewModel() {

    val tripList: LiveData<List<Trip>> = tripRepository.getTripList()
}