package com.sosnowskydevelop.tripmanager.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.sosnowskydevelop.tripmanager.data.trip.TripRepository

class TripListViewModelFactory(
    private val repository: TripRepository
) : ViewModelProvider.Factory {

    @Suppress ("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        return TripListViewModel(repository) as T
    }
}