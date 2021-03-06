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
    private lateinit var binding: FragmentTripListBinding
    private val viewModel: TripListViewModel by viewModels {
        InjectorUtils.provideTripListViewModelFactory(this)
    }

    override fun onCreateView(inflate: LayoutInflater, container: ViewGroup?,
                              saveInstanceState: Bundle?): View {
        binding = FragmentTripListBinding.inflate(inflate, container, false)
        binding.viewModel = viewModel
        val adapter = TripAdapter(requireContext(), this)
        binding.tripList.adapter = adapter
        subscribeUi(adapter)
        binding.addTripButton.setOnClickListener {
            findNavController().navigate(R.id.action_tripListFragment_to_tripAddFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: TripAdapter) {
        viewModel.tripList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.updateTripList(it)
            }
        })
    }
}