package com.sosnowskydevelop.tripmanager.utilities

import android.content.Context
import com.sosnowskydevelop.tripmanager.data.AppDatabase
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.data.TripRepository
import com.sosnowskydevelop.tripmanager.viewmodels.*

object InjectorUtils {

    private fun getTripRepository(context: Context): TripRepository {
        return TripRepository.getInstance(tripDao = AppDatabase
                .getInstance(context = context.applicationContext).tripDao())
    }

    private fun getRefuelingRepository(context: Context): RefuelingRepository {
        return RefuelingRepository.getInstance(refuelingDao = AppDatabase
                .getInstance(context = context.applicationContext).refuelingDao())
    }

    fun provideTripListViewModelFactory(context: Context): TripListViewModelFactory {
        return TripListViewModelFactory(
                tripRepository = getTripRepository(context = context))
    }

    fun provideTripAddViewModelFactory(context: Context): TripAddViewModelFactory {
        return TripAddViewModelFactory(
                tripRepository = getTripRepository(context = context))
    }

    fun provideRefuelingListViewModelFactory(context: Context): RefuelingListViewModelFactory {
        return RefuelingListViewModelFactory(
                refuelingRepository = getRefuelingRepository(context = context))
    }

    fun provideRefuelingAddViewModelFactory(context: Context): RefuelingAddViewModelFactory {
        return RefuelingAddViewModelFactory(
                refuelingRepository = getRefuelingRepository(context = context))
    }

    fun provideRefuelingInfoViewModelFactory(context: Context): RefuelingInfoViewModelFactory {
        return RefuelingInfoViewModelFactory(
                refuelingRepository = getRefuelingRepository(context = context))
    }
}