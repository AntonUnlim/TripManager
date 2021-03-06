package com.sosnowskydevelop.tripmanager.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.sosnowskydevelop.tripmanager.utilities.NOT_TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager.utilities.NOT_TO_FULL_LOWER
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_LOWER
import java.io.Serializable

@Entity(tableName = "refueling")
data class Refueling (
    val odometer: String,
    val litersFilled: String,
    val pricePerLiter: String,
    val isToFull: Boolean
) : Parcelable {
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(odometer)
        parcel.writeString(litersFilled)
        parcel.writeString(pricePerLiter)
        parcel.writeBoolean(isToFull)
    }

    override fun describeContents() = 0
}