package com.sosnowskydevelop.tripmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.adapters.RefuelingAdapter
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingListBinding
import com.sosnowskydevelop.tripmanager.utilities.BUNDLE_KEY_LAST_REFUELING
import com.sosnowskydevelop.tripmanager.utilities.InjectorUtils
import com.sosnowskydevelop.tripmanager.utilities.REQUEST_KEY_LAST_REFUELING
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingListViewModel

class RefuelingListFragment : Fragment() {

    private lateinit var binding: FragmentRefuelingListBinding

    private val viewModel: RefuelingListViewModel by viewModels {
        InjectorUtils.provideRefuelingListViewModelFactory(this)
    }

    private var lastRefueling: Refueling? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRefuelingListBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        val adapter = RefuelingAdapter(requireContext(), this)
        binding.refuelingList.adapter = adapter
        subscribeUi(adapter)

        binding.addRefuelingButton.setOnClickListener {
            setFragmentResult(REQUEST_KEY_LAST_REFUELING,
                    bundleOf(BUNDLE_KEY_LAST_REFUELING to lastRefueling))
            findNavController().navigate(R.id.action_refuelingListFragment_to_refuelingAddFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun subscribeUi(adapter: RefuelingAdapter) {
        viewModel.refuelingList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.updateRefuelingList(it)

                lastRefueling = it.lastOrNull()

                viewModel.updateAverageFuelConsumption(it)
            }
        })
    }
}