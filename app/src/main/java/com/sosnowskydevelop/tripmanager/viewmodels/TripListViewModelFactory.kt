package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sosnowskydevelop.tripmanager.data.TripRepository

class TripListViewModelFactory(
    private val tripRepository: TripRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        return TripListViewModel(tripRepository = tripRepository) as T
    }
}