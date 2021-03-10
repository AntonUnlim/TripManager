package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.SharedViewModel
import com.sosnowskydevelop.tripmanager.SharedViewModelFactory
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingAddBinding
import com.sosnowskydevelop.tripmanager.utilities.*
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingAddViewModel
import kotlin.reflect.KClass

class RefuelingAddFragment : Fragment() {

    private lateinit var fragmentRefuelingAddBinding: FragmentRefuelingAddBinding

//    private val refuelingAddViewModel: RefuelingAddViewModel by viewModels {
//        InjectorUtils.provideRefuelingAddViewModelFactory(context = requireContext())
//    }

    private lateinit var refuelingAddViewModel: SharedViewModel

    private var lastRefueling: Refueling? = null
    private var parentTripID: Long = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentRefuelingAddBinding = FragmentRefuelingAddBinding.inflate(inflater, container, false)
        return fragmentRefuelingAddBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refuelingAddViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        refuelingAddViewModel.refuelingRepository = InjectorUtils.getRefuelingRepository(requireContext())

        fragmentRefuelingAddBinding.viewModel = refuelingAddViewModel

        fragmentRefuelingAddBinding.saveButton.setOnClickListener {
            refuelingAddViewModel.createRefueling()
            if (refuelingAddViewModel.isRefuelingCreated) {
                findNavController()
                        .navigate(R.id.action_refuelingAddFragment_to_refuelingListFragment)
            }
        }

        setFragmentResultListener(requestKey = REQUEST_KEY_LAST_REFUELING) { _, bundle ->
            lastRefueling = bundle.get(BUNDLE_KEY_LAST_REFUELING) as Refueling?
            refuelingAddViewModel.initLastRefueling(lastRefueling = lastRefueling)
        }

        setFragmentResultListener(requestKey = REQUEST_KEY_REFUELING_ADD_TRIP_ID) { _, bundle ->
            parentTripID = bundle.getLong(BUNDLE_KEY_REFUELING_ADD_TRIP_ID)
            refuelingAddViewModel.initParentTrip(parentTripID)
        }
    }
}