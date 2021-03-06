package com.sosnowskydevelop.tripmanager.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.sosnowskydevelop.tripmanager.data.AppDatabase
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingAddViewModelFactory
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingListViewModelFactory
import com.sosnowskydevelop.tripmanager.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingInfoViewModelFactory

object InjectorUtils {

    fun getRefuelingRepository(context: Context): RefuelingRepository {
        return RefuelingRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).refuelingDao())
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
}