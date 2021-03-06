package com.sosnowskydevelop.tripmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingInfoBinding
import com.sosnowskydevelop.tripmanager.utilities.*
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingInfoViewModel

class RefuelingInfoFragment : Fragment() {

    private lateinit var binding: FragmentRefuelingInfoBinding

    private val viewModel: RefuelingInfoViewModel by viewModels {
        InjectorUtils.provideRefuelingInfoViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_REFUELING) { _, bundle ->

            val result = bundle.getLong(BUNDLE_KEY_REFUELING)

            viewModel.initRefueling(result)

            subscribeUI()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRefuelingInfoBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.deleteButton.setOnClickListener {
            viewModel.removeRefueling()
            findNavController().navigate(R.id.action_refuelingInfoFragment_to_refuelingListFragment)
        }

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.refueling.observe(viewLifecycleOwner, {
            it?.let {

                viewModel.odometer.set(it.odometer)

                viewModel.litersFilled.set(it.litersFilled)

                viewModel.pricePerLiter.set(it.pricePerLiter)

                if (it.isToFull) {
                    viewModel.isToFull.set(TO_FULL_LOWER)
                } else {
                    viewModel.isToFull.set(NOT_TO_FULL_LOWER)
                }
            }
        })
    }
}