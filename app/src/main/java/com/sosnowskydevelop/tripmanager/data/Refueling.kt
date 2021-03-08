package com.sosnowskydevelop.tripmanager.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sosnowskydevelop.tripmanager.utilities.NOT_TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager.utilities.NOT_TO_FULL_LOWER
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager.utilities.TO_FULL_LOWER

@Entity(tableName = "refueling")
data class Refueling (
    val odometer: String,
    val litersFilled: String,
    val pricePerLiter: String,
    val isToFull: Boolean,
    val tripId: Long
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var refuelingId: Long = 0

    @get: Ignore
    val isToFullCapitalize: String
        get() {
            return if (isToFull) {
                TO_FULL_CAPITALIZE
            } else {
                NOT_TO_FULL_CAPITALIZE
            }
        }

    @get: Ignore
    val isToFullLower: String
        get() {
            return if (isToFull) {
                TO_FULL_LOWER
            } else {
                NOT_TO_FULL_LOWER
            }
        }

    constructor(parcel: Parcel) : this(
        odometer = parcel.readString().toString(),
        litersFilled = parcel.readString().toString(),
        pricePerLiter = parcel.readString().toString(),
        isToFull = parcel.readByte() != 0.toByte(),
        tripId = parcel.readLong()
    ) {
        refuelingId = parcel.readLong()
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(odometer)
        parcel.writeString(litersFilled)
        parcel.writeString(pricePerLiter)
        parcel.writeBoolean(isToFull)
        parcel.writeLong(tripId)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Refueling> {
        override fun createFromParcel(parcel: Parcel): Refueling {
            return Refueling(parcel)
        }

        override fun newArray(size: Int): Array<Refueling?> {
            return arrayOfNulls(size)
        }
    }
}