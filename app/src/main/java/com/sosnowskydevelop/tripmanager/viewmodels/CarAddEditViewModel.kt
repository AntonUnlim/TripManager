package com.sosnowskydevelop.tripmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.sosnowskydevelop.tripmanager.data.CarRepository

class CarAddEditViewModel : ViewModel() {
    private val carRepository = CarRepository.instance

    fun addCar(shortName: String, fuel: String) {
        carRepository.addCar(shortName = shortName, fuel = fuel)
    }
}