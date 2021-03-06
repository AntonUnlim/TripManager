package com.sosnowskydevelop.tripmanager.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.sosnowskydevelop.tripmanager.data.AppDatabase
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.data.trip.TripRepository
import com.sosnowskydevelop.tripmanager.viewmodels.*

object InjectorUtils {

    fun getRefuelingRepository(context: Context): RefuelingRepository {
        return RefuelingRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).refuelingDao())
    }

    fun getTripRepository(context: Context): TripRepository {
        return TripRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).tripDao()
        )
    }

    fun provideRefuelingListViewModelFactory(fragment: Fragment): RefuelingListViewModelFactory {
        return RefuelingListViewModelFactory(
            getRefuelingRepository(fragment.requireContext()), fragment)
    }

    fun provideRefuelingAddViewModelFactory(context: Context): RefuelingAddViewModelFactory {
        return RefuelingAddViewModelFactory(getRefuelingRepository(context))
    }

    fun provideRefuelingInfoViewModelFactory(context: Context): RefuelingInfoViewModelFactory {
        return RefuelingInfoViewModelFactory(getRefuelingRepository(context))
    }

    fun provideTripListViewModelFactory(fragment: Fragment): TripListViewModelFactory {
        return TripListViewModelFactory(
            getTripRepository(fragment.requireContext()), fragment
        )
    }

    fun provideTripAddViewModelFactory(context: Context): TripAddViewModelFactory {
        return TripAddViewModelFactory(getTripRepository(context))
    }
}