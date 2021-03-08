package com.sosnowskydevelop.tripmanager

import android.os.Bundle
import android.util.Log
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
        InjectorUtils.provideRefuelingListViewModelFactory(requireContext())
    }

    private var parentTripID: Long = 0
    private val refuelingAdapter: RefuelingAdapter = RefuelingAdapter(this)
    private var lastRefueling: Refueling? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_TRIP_ID_FOR_REFUELING_LIST) { _, bundle ->
            parentTripID = bundle.getLong(BUNDLE_KEY_TRIP_ID_FOR_REFUELING_LIST)
            viewModel.initTrip(parentTripID)
            addObserverForRefuelingList(refuelingAdapter)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRefuelingListBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.refuelingList.adapter = refuelingAdapter

        // TODO Temp
        addObserverForRefuelingList(refuelingAdapter)

        binding.addRefuelingButton.setOnClickListener {

            setFragmentResult(REQUEST_KEY_LAST_REFUELING,
                    bundleOf(BUNDLE_KEY_LAST_REFUELING to lastRefueling))

            setFragmentResult(REQUEST_KEY_TRIP_ID_FOR_REFUELING_ADD,
                    bundleOf(BUNDLE_KEY_TRIP_ID_FOR_REFUELING_ADD to parentTripID))

            findNavController()
                    .navigate(R.id.action_refuelingListFragment_to_refuelingAddFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun addObserverForRefuelingList(refuelingAdapter: RefuelingAdapter) {
        viewModel.refuelingList.observe(viewLifecycleOwner, {
            it?.let {
                refuelingAdapter.updateRefuelingList(it)

                lastRefueling = it.lastOrNull()

                viewModel.updateAverageFuelConsumption(it)
            }
        })
    }
}