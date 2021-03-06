package com.sosnowskydevelop.tripmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.adapters.RefuelingAdapter
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingListBinding
import com.sosnowskydevelop.tripmanager.utilities.*
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingListViewModel

class RefuelingListFragment : Fragment() {

    private lateinit var binding: FragmentRefuelingListBinding

    private val viewModel: RefuelingListViewModel by viewModels {
        InjectorUtils.provideRefuelingListViewModelFactory(this)
    }

    private var lastRefueling: Refueling? = null
    private var parentTripID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_TRIP) { _, bundle ->

            parentTripID = bundle.getLong(BUNDLE_KEY_TRIP)

            viewModel.initTrip(parentTripID)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRefuelingListBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        val adapter = RefuelingAdapter(requireContext(), this)
        binding.refuelingList.adapter = adapter
        subscribeUI(adapter)

        binding.addRefuelingButton.setOnClickListener {
            setFragmentResult(REQUEST_KEY_LAST_REFUELING,
                    bundleOf(BUNDLE_KEY_LAST_REFUELING to lastRefueling))
            setFragmentResult(
                REQUEST_KEY_TRIP,
                bundleOf(BUNDLE_KEY_TRIP to parentTripID))
            findNavController().navigate(R.id.action_refuelingListFragment_to_refuelingAddFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun subscribeUI(adapter: RefuelingAdapter) {
        viewModel.refuelingList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.updateRefuelingList(it)

                lastRefueling = it.lastOrNull()

                viewModel.updateAverageFuelConsumption(it)
            }
        })
    }
}