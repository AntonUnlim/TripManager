package com.sosnowskydevelop.tripmanager.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sosnowskydevelop.tripmanager.data.trip.TripRepository

class TripListViewModelFactory(
    private val repository: TripRepository,
    owner: SavedStateRegistryOwner,
    defaultAgrs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultAgrs) {

    @Suppress ("UNCHECKED_CAST")
    override fun <T: ViewModel> create (
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return TripListViewModel(repository) as T
    }
}