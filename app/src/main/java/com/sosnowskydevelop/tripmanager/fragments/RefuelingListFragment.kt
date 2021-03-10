package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.SharedViewModel
import com.sosnowskydevelop.tripmanager.SharedViewModelFactory
import com.sosnowskydevelop.tripmanager.adapters.RefuelingAdapter
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingListBinding
import com.sosnowskydevelop.tripmanager.utilities.*
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingListViewModel

class RefuelingListFragment : Fragment() {

    private lateinit var fragmentRefuelingListBinding: FragmentRefuelingListBinding

//    private val refuelingListViewModel: RefuelingListViewModel by viewModels {
//        InjectorUtils.provideRefuelingListViewModelFactory(context = requireContext())
//    }

    private lateinit var refuelingListViewModel: SharedViewModel

    private var parentTripID: Long = 0
    private val refuelingAdapter: RefuelingAdapter = RefuelingAdapter(this)
    private var lastRefueling: Refueling? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentRefuelingListBinding = FragmentRefuelingListBinding.inflate(inflater, container, false)
        return fragmentRefuelingListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refuelingListViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        refuelingListViewModel.refuelingRepository = InjectorUtils.getRefuelingRepository(requireContext())

        fragmentRefuelingListBinding.viewModel = refuelingListViewModel

        // TEMP
//        val refuelingAdapter = RefuelingAdapter(fragment = this)
        fragmentRefuelingListBinding.refuelingList.adapter = refuelingAdapter
        // TEMP
//        addObserverForRefuelingList(refuelingAdapter)

        // TEMP
//        setFragmentResultListener(REQUEST_KEY_REFUELING_LIST_TRIP_ID) { _, bundle ->
//            parentTripID = bundle.getLong(BUNDLE_KEY_REFUELING_LIST_TRIP_ID)
//            refuelingListViewModel.initTrip(parentTripID)
//            addObserverForRefuelingList(refuelingAdapter)
//        }

        fragmentRefuelingListBinding.addRefuelingButton.setOnClickListener {

            setFragmentResult(REQUEST_KEY_LAST_REFUELING,
                    bundleOf(BUNDLE_KEY_LAST_REFUELING to lastRefueling))

            setFragmentResult(REQUEST_KEY_REFUELING_ADD_TRIP_ID,
                    bundleOf(BUNDLE_KEY_REFUELING_ADD_TRIP_ID to parentTripID))

            findNavController()
                    .navigate(R.id.action_refuelingListFragment_to_refuelingAddFragment)
        }

        setHasOptionsMenu(true)

        setFragmentResultListener(REQUEST_KEY_REFUELING_LIST_TRIP_ID) { _, bundle ->
            parentTripID = bundle.getLong(BUNDLE_KEY_REFUELING_LIST_TRIP_ID)
            refuelingListViewModel.initTrip(parentTripID)
            addObserverForRefuelingList(refuelingAdapter)
        }
    }

    private fun addObserverForRefuelingList(refuelingAdapter: RefuelingAdapter) {
//        refuelingListViewModel.refuelingList.observeForever {
//            it?.let {
//                refuelingAdapter.updateRefuelingList(it)
//
//                lastRefueling = it.lastOrNull()
//
//                refuelingListViewModel.updateAverageFuelConsumption(it)
//            }
//        }
        refuelingListViewModel.refuelingList.observe(viewLifecycleOwner, {
            it?.let {
                refuelingAdapter.updateRefuelingList(it)

                lastRefueling = it.lastOrNull()

                refuelingListViewModel.updateAverageFuelConsumption(it)
            }
        })
    }
}