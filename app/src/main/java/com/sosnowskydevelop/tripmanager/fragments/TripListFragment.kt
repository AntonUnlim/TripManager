package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import android.util.Log
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
import com.sosnowskydevelop.tripmanager.utilities.LOG_TAG
import com.sosnowskydevelop.tripmanager.viewmodels.TripListViewModel

class TripListFragment : Fragment() {

    private lateinit var fragmentTripListBinding: FragmentTripListBinding

    private val tripListViewModel: TripListViewModel by viewModels {
        InjectorUtils.provideTripListViewModelFactory(context = requireContext())
    }

    private lateinit var tripAdapter: TripAdapter

    override fun onCreateView(
            inflate: LayoutInflater,
            container: ViewGroup?,
            saveInstanceState: Bundle?
    ): View {
        fragmentTripListBinding = FragmentTripListBinding
                .inflate(inflate, container, false)
        return fragmentTripListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentTripListBinding.viewModel = tripListViewModel

        tripAdapter = TripAdapter(fragment = this)
        fragmentTripListBinding.tripList.adapter = tripAdapter
        addObserverForTripList(tripAdapter = tripAdapter)

        fragmentTripListBinding.addTripButton.setOnClickListener {
            findNavController()
                    .navigate(R.id.action_tripListFragment_to_tripAddFragment)
        }

        setHasOptionsMenu(true)
    }

    private fun addObserverForTripList(tripAdapter: TripAdapter) {
        tripListViewModel.tripList.observe(viewLifecycleOwner, {
            it?.let {
                tripAdapter.submitList(it)
            }
        })
    }
}