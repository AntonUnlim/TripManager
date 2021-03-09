package com.sosnowskydevelop.tripmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.databinding.FragmentCarAddEditBinding
import com.sosnowskydevelop.tripmanager.viewmodels.CarAddEditViewModel


class CarAddEditFragment : Fragment() {

    companion object {
        fun newInstance() = CarAddEditFragment()
    }

    private lateinit var binding: FragmentCarAddEditBinding

    private lateinit var viewModel: CarAddEditViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(CarAddEditViewModel::class.java)

        binding = FragmentCarAddEditBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val adapter = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, arrayListOf("Дизель", "Бензин"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fuelSpinner.adapter = adapter

        binding.saveButton.setOnClickListener {
            viewModel.addCar(shortName = binding.shortNameEditText.text.toString(), fuel = binding.fuelSpinner.selectedItem.toString())
            findNavController().navigate(R.id.action_carAddEditFragment_to_carListFragment)
        }

        return binding.root
    }
}