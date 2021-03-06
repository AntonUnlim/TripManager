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

//    private lateinit var sharedViewModel: SharedViewModel

    private var lastRefueling: Refueling? = null
    private var tripId: Long = 0

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

//        val refuelingRepository = InjectorUtils.getRefuelingRepository(context = requireContext())
//        val sharedViewModelFactory =
//                SharedViewModelFactory(refuelingRepository = refuelingRepository/*, tripId = tripId*/)
//        sharedViewModel = ViewModelProvider(this/*requireActivity()*/, sharedViewModelFactory)
//                .get(SharedViewModel::class.java)

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
            tripId = bundle.getLong(BUNDLE_KEY_REFUELING_ADD_TRIP_ID)
            refuelingAddViewModel.initParentTrip(tripId)
        }
    }
}