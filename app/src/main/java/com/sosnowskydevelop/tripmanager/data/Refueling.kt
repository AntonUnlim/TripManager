package com.sosnowskydevelop.tripmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sosnowskydevelop.tripmanager.utilities.NOT_TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager.utilities.NOT_TO_FULL_LOWER
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_LOWER

@Entity(tableName = "refueling")
data class Refueling(
    val odometer: String,
    val litersFilled: String,
    val pricePerLiter: String,
    val isToFull: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var refuelingId: Long = 0

    val isToFullCapitalize: String
        get() {
            return if (isToFull) {
                TO_FULL_CAPITALIZE
            } else {
                NOT_TO_FULL_CAPITALIZE
            }
        }

    val isToFullLower: String
        get() {
            return if (isToFull) {
                TO_FULL_LOWER
            } else {
                NOT_TO_FULL_LOWER
            }
        }
}