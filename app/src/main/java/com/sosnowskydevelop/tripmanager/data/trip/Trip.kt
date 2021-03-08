package com.sosnowskydevelop.tripmanager.data.trip

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sosnowskydevelop.tripmanager.data.Refueling
import java.util.*
import kotlin.collections.ArrayList

@Entity (tableName = "Trip")
data class Trip (val name: String, val beginDate: Date) {
    @PrimaryKey(autoGenerate = true)
    var tripId: Long = 0
    @Ignore
    val listOfRefuelings: List<Refueling> = ArrayList()
    var endDate: Date? = null
}
