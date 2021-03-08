package com.sosnowskydevelop.tripmanager.utilities

import android.content.Context
import com.sosnowskydevelop.tripmanager.data.AppDatabase
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.data.trip.TripRepository
import com.sosnowskydevelop.tripmanager.viewmodels.*

object InjectorUtils {

    private fun getTripRepository(context: Context): TripRepository {
        return TripRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).tripDao())
    }

    private fun getRefuelingRepository(context: Context): RefuelingRepository {
        return RefuelingRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).refuelingDao())
    }

    fun provideTripListViewModelFactory(context: Context): TripListViewModelFactory {
        return TripListViewModelFactory(getTripRepository(context))
    }

    fun provideTripAddViewModelFactory(context: Context): TripAddViewModelFactory {
        return TripAddViewModelFactory(getTripRepository(context))
    }

    fun provideRefuelingListViewModelFactory(context: Context): RefuelingListViewModelFactory {
        return RefuelingListViewModelFactory(getRefuelingRepository(context))
    }

    fun provideRefuelingAddViewModelFactory(context: Context): RefuelingAddViewModelFactory {
        return RefuelingAddViewModelFactory(getRefuelingRepository(context))
    }

    fun provideRefuelingInfoViewModelFactory(context: Context): RefuelingInfoViewModelFactory {
        return RefuelingInfoViewModelFactory(getRefuelingRepository(context))
    }
}