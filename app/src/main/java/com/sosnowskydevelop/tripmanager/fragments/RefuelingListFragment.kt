package com.sosnowskydevelop.tripmanager.fragments

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
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.adapters.RefuelingAdapter
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingListBinding
import com.sosnowskydevelop.tripmanager.utilities.*
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingListViewModel

class RefuelingListFragment : Fragment() {

    private lateinit var fragmentRefuelingListBinding: FragmentRefuelingListBinding

    private val refuelingListViewModel: RefuelingListViewModel by viewModels {
        InjectorUtils.provideRefuelingListViewModelFactory(context = requireContext())
    }

//    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var refuelingAdapter: RefuelingAdapter

    private var tripId: Long = 0
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

//        val refuelingRepository =
//                InjectorUtils.getRefuelingRepository(context = requireContext())
//        val sharedViewModelFactory = SharedViewModelFactory(
//                refuelingRepository = refuelingRepository/*, tripId = tripId*/)
//        sharedViewModel = ViewModelProvider(this/*requireActivity()*/, sharedViewModelFactory)
//                .get(SharedViewModel::class.java)

        fragmentRefuelingListBinding.viewModel = refuelingListViewModel

        refuelingAdapter = RefuelingAdapter(fragment = this)
        fragmentRefuelingListBinding.refuelingList.adapter = refuelingAdapter
        addObserverForRefuelingList(refuelingAdapter)

//        setFragmentResultListener(REQUEST_KEY_REFUELING_LIST_TRIP_ID) { _, bundle ->
//            parentTripID = bundle.getLong(BUNDLE_KEY_REFUELING_LIST_TRIP_ID)
//            refuelingListViewModel.initTrip(parentTripID)
//            addObserverForRefuelingList(refuelingAdapter)
//        }

        fragmentRefuelingListBinding.addRefuelingButton.setOnClickListener {

            setFragmentResult(REQUEST_KEY_LAST_REFUELING,
                    bundleOf(BUNDLE_KEY_LAST_REFUELING to lastRefueling))

            setFragmentResult(REQUEST_KEY_REFUELING_ADD_TRIP_ID,
                    bundleOf(BUNDLE_KEY_REFUELING_ADD_TRIP_ID to tripId))

            findNavController()
                    .navigate(R.id.action_refuelingListFragment_to_refuelingAddFragment)
        }

        setHasOptionsMenu(true)

        setFragmentResultListener(REQUEST_KEY_REFUELING_LIST_TRIP_ID) { _, bundle ->
            tripId = bundle.getLong(BUNDLE_KEY_REFUELING_LIST_TRIP_ID)
            refuelingListViewModel.initTrip(tripId = tripId)
//            addObserverForRefuelingList(refuelingAdapter)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_TAG, refuelingAdapter.currentList.toString())
    }

    private fun addObserverForRefuelingList(refuelingAdapter: RefuelingAdapter) {
        refuelingListViewModel.refuelingList.observe(viewLifecycleOwner, {
            it?.let {
                refuelingAdapter.submitList(it)
                Log.i(LOG_TAG, "obs" + refuelingAdapter.currentList.toString())
                lastRefueling = it.lastOrNull()
                refuelingListViewModel.updateAverageFuelConsumption(refuelingList = it)
            }
        })
    }
}