package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sosnowskydevelop.tripmanager.data.TripRepository

class TripAddViewModelFactory(
    private val tripRepository: TripRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TripAddViewModel(tripRepository = tripRepository) as T
    }
}

