package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.sosnowskydevelop.tripmanager.data.trip.TripRepository

class TripAddViewModelFactory(
    private val repository: TripRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TripAddViewModel(repository) as T
    }
}

