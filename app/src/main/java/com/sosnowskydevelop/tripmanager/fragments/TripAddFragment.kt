package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.databinding.FragmentTripAddBinding
import com.sosnowskydevelop.tripmanager.utilities.InjectorUtils
import com.sosnowskydevelop.tripmanager.viewmodels.TripAddViewModel

class TripAddFragment : Fragment() {

    private lateinit var fragmentTripAddBinding: FragmentTripAddBinding

    private val tripAddViewModel: TripAddViewModel by viewModels {
        InjectorUtils.provideTripAddViewModelFactory(context = requireContext())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentTripAddBinding = FragmentTripAddBinding
                .inflate(inflater, container, false)
        fragmentTripAddBinding.viewModel = tripAddViewModel

        fragmentTripAddBinding.tripSaveButton.setOnClickListener {
            tripAddViewModel.createTrip()
            if (tripAddViewModel.isTripCreated) {

            findNavController().navigate(R.id.action_tripAddFragment_to_tripListFragment)
            }
        }

        return fragmentTripAddBinding.root
    }
}