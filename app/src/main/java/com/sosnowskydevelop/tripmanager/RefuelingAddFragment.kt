package com.sosnowskydevelop.tripmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingAddBinding
import com.sosnowskydevelop.tripmanager.utilities.BUNDLE_KEY_LAST_REFUELING
import com.sosnowskydevelop.tripmanager.utilities.InjectorUtils
import com.sosnowskydevelop.tripmanager.utilities.REQUEST_KEY_LAST_REFUELING
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingAddViewModel

class RefuelingAddFragment : Fragment() {

    private lateinit var binding: FragmentRefuelingAddBinding

    private val viewModel: RefuelingAddViewModel by viewModels {
        InjectorUtils.provideRefuelingAddViewModelFactory(requireContext())
    }

    private var lastRefueling: Refueling? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_LAST_REFUELING) { _, bundle ->
            lastRefueling = bundle.get(BUNDLE_KEY_LAST_REFUELING) as Refueling?

            viewModel.initLastRefueling(lastRefueling)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRefuelingAddBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.saveButton.setOnClickListener {
            viewModel.createRefueling()
            if (viewModel.isRefuelingCreated) {
                findNavController()
                        .navigate(R.id.action_refuelingAddFragment_to_refuelingListFragment)
            }
        }

        return binding.root
    }
}