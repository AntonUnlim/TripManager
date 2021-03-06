package com.sosnowskydevelop.tripmanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sosnowskydevelop.tripmanager.adapters.CarAdapter
import com.sosnowskydevelop.tripmanager.databinding.FragmentCarListBinding
import com.sosnowskydevelop.tripmanager.viewmodels.CarListViewModel

class CarListFragment : Fragment() {

    companion object {
        fun newInstance() =
            CarListFragment()
    }

    private lateinit var binding: FragmentCarListBinding

    private lateinit var viewModel: CarListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(CarListViewModel::class.java)

        binding = FragmentCarListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.carList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter =
                CarAdapter(viewModel.cars)
        }

        binding.addCarButton.setOnClickListener {
            findNavController().navigate(R.id.action_carListFragment_to_carAddEditFragment)
        }

        return binding.root
    }
}