package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingAddBinding
import com.sosnowskydevelop.tripmanager.utilities.*
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingAddViewModel

class RefuelingAddFragment : Fragment() {

    private lateinit var fragmentRefuelingAddBinding: FragmentRefuelingAddBinding

    private val refuelingAddViewModel: RefuelingAddViewModel by viewModels {
        InjectorUtils.provideRefuelingAddViewModelFactory(context = requireContext())
    }

    private var lastRefueling: Refueling? = null
    private var parentTripID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(requestKey = REQUEST_KEY_LAST_REFUELING) { _, bundle ->
            lastRefueling = bundle.get(BUNDLE_KEY_LAST_REFUELING) as Refueling?
            refuelingAddViewModel.initLastRefueling(lastRefueling = lastRefueling)
        }

        setFragmentResultListener(requestKey = REQUEST_KEY_REFUELING_ADD_TRIP_ID) { _, bundle ->
            parentTripID = bundle.getLong(BUNDLE_KEY_REFUELING_ADD_TRIP_ID)
            refuelingAddViewModel.initParentTrip(parentTripID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentRefuelingAddBinding = FragmentRefuelingAddBinding.inflate(inflater, container, false)

        fragmentRefuelingAddBinding.viewModel = refuelingAddViewModel

        fragmentRefuelingAddBinding.saveButton.setOnClickListener {
            refuelingAddViewModel.createRefueling()
            if (refuelingAddViewModel.isRefuelingCreated) {
                findNavController()
                        .navigate(R.id.action_refuelingAddFragment_to_refuelingListFragment)
            }
        }

        return fragmentRefuelingAddBinding.root
    }
}