package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.adapters.TripAdapter
import com.sosnowskydevelop.tripmanager.databinding.FragmentTripListBinding
import com.sosnowskydevelop.tripmanager.utilities.InjectorUtils
import com.sosnowskydevelop.tripmanager.viewmodels.TripListViewModel

class TripListFragment : Fragment() {

    private lateinit var fragmentTripListBinding: FragmentTripListBinding

    private val tripListViewModel: TripListViewModel by viewModels {
        InjectorUtils.provideTripListViewModelFactory(context = requireContext())
    }

    override fun onCreateView(
            inflate: LayoutInflater,
            container: ViewGroup?,
            saveInstanceState: Bundle?
    ): View {
        fragmentTripListBinding = FragmentTripListBinding
                .inflate(inflate, container, false)
        fragmentTripListBinding.viewModel = tripListViewModel

        val tripAdapter = TripAdapter(fragment = this)
        fragmentTripListBinding.tripList.adapter = tripAdapter
        addObserverForTripList(tripAdapter = tripAdapter)

        fragmentTripListBinding.addTripButton.setOnClickListener {
            findNavController()
                    .navigate(R.id.action_tripListFragment_to_tripAddFragment)
        }

        setHasOptionsMenu(true)

        return fragmentTripListBinding.root
    }

    private fun addObserverForTripList(tripAdapter: TripAdapter) {
        tripListViewModel.tripList.observe(viewLifecycleOwner, {
            it?.let {
                tripAdapter.updateTripList(tripList = it)
            }
        })
    }
}