package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager.data.CarRepository
import com.sosnowskydevelop.tripmanager.data.Car
import kotlinx.coroutines.launch

class CarListViewModel : ViewModel() {
    private val carRepository = CarRepository.instance

    val cars: ArrayList<Car>
        get() {
            return carRepository.getCars()
        }
}