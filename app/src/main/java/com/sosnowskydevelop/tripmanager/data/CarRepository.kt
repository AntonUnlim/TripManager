package com.sosnowskydevelop.tripmanager.data

class CarRepository {
    // TODO переделать на сохранение в базу и на LiveData
    private val carList = ArrayList<Car>()

    fun getCars(): ArrayList<Car> {
        //temp
        if (carList.isEmpty()) {
            carList.add(
                Car(
                    _shortName = "TestCar1",
                    _fuel = Fuel.DIESEL
                )
            )
            carList.add(
                Car(
                    _shortName = "TestCar2",
                    _fuel = Fuel.PETROL
                )
            )
        }
        return carList
    }

    fun addCar(shortName: String, fuel: String) {
        // TODO переделать определение типа топлива
        if (fuel == "Дизель") {
            carList.add(Car(_shortName = shortName, _fuel = Fuel.DIESEL))
        } else {
            carList.add(Car(_shortName = shortName, _fuel = Fuel.PETROL))
        }
    }

    companion object {
        val instance = CarRepository()
    }
}