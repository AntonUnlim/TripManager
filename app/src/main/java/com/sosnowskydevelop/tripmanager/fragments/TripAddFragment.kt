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
    private lateinit var binding: FragmentTripAddBinding
    private val viewModel: TripAddViewModel by viewModels {
        InjectorUtils.provideTripAddViewModelFactory(requireContext())
    }

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View {
        binding = FragmentTripAddBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.tripSaveButton.setOnClickListener {
            viewModel.createTrip()
            if (viewModel.isTripCreated) {
                findNavController()
                    .navigate(R.id.action_tripAddFragment_to_tripListFragment)
            }
        }

        return binding.root
    }
}