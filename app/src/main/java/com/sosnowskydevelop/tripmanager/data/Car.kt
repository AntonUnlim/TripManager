package com.sosnowskydevelop.tripmanager.data

data class Car(var _shortName: String, val _fuel: Fuel) {

    var shortName: String = _shortName
    var fullName: String? = null
    val fuel: Fuel = _fuel
    var odometer: Int = 0
        set(value) {
            if (value < 0)
                field = 0
            else field = value
        }
    var fuelTank: Int = 0
        set(value) {
            if (value < 0)
                field = 0
            else field = value
        }
    var consumption: Float = 0f
        set(value) {
            if (value < 0)
                field = 0f
            else field = value
        }
    fun odometerIncrease(distance: Int): Int {
        odometer += distance
        return odometer
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Car

        if (shortName == other.shortName) return false

        return true
    }
    override fun hashCode(): Int {
        return 31 * shortName.hashCode()
    }
    override fun toString(): String {
        return shortName
    }
}