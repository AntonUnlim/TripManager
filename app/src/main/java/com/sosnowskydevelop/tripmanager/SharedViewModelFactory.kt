package com.sosnowskydevelop.tripmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository

class SharedViewModelFactory(
        private val refuelingRepository: RefuelingRepository//,
        //private val tripId: Long
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(refuelingRepository/*, tripId*/) as T
    }
}