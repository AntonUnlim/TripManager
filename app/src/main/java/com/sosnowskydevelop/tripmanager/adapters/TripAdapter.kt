package com.sosnowskydevelop.tripmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.data.trip.Trip
import com.sosnowskydevelop.tripmanager.databinding.ListItemTripBinding
import com.sosnowskydevelop.tripmanager.utilities.BUNDLE_KEY_TRIP
import com.sosnowskydevelop.tripmanager.utilities.REQUEST_KEY_TRIP
import com.sosnowskydevelop.tripmanager.viewmodels.TripListItemViewModel

class TripAdapter (
    private val fragment: Fragment
) : ListAdapter<Trip, RecyclerView.ViewHolder>(TripDiffCallback()) {

    private lateinit var tripList: List<Trip>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.TripViewHolder {
        return TripViewHolder(
            ListItemTripBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TripAdapter.TripViewHolder).bind(getItem(position))
    }

    fun updateTripList(tripList: List<Trip>) {
        this.tripList = tripList
        submitList(tripList)
        notifyDataSetChanged()
    }

    inner class TripViewHolder(
        private val binding: ListItemTripBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Trip) {

            binding.viewModel = TripListItemViewModel(item)

            binding.executePendingBindings()

            binding.container.setOnClickListener {

                val result = item.tripId

                fragment.setFragmentResult(
                    REQUEST_KEY_TRIP, bundleOf(BUNDLE_KEY_TRIP to result)
                )

                fragment.findNavController()
                    .navigate(R.id.action_tripListFragment_to_refuelingListFragment)
            }
        }
    }
}



private class TripDiffCallback : DiffUtil.ItemCallback<Trip>() {

    override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem.tripId == newItem.tripId
    }

    override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem == newItem
    }
}