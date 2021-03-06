package com.sosnowskydevelop.tripmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.databinding.FragmentRefuelingAddBinding
import com.sosnowskydevelop.tripmanager.utilities.InjectorUtils
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingAddViewModel

// TODO не сохранять фрагмент в истории (stack)
class RefuelingAddFragment : Fragment() {

    private lateinit var binding: FragmentRefuelingAddBinding

    private val viewModel: RefuelingAddViewModel by viewModels {
        InjectorUtils.provideRefuelingAddViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRefuelingAddBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.saveButton.setOnClickListener {
            viewModel.createRefueling()
            findNavController().navigate(R.id.action_refuelingAddFragment_to_refuelingListFragment)
        }

        return binding.root
    }
}