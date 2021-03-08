package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository

class RefuelingListViewModelFactory(
        private val repository: RefuelingRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RefuelingListViewModel(repository) as T
    }
}