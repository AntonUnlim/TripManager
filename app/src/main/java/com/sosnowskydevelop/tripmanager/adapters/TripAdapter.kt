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
import com.sosnowskydevelop.tripmanager.data.Trip
import com.sosnowskydevelop.tripmanager.databinding.ListItemTripBinding
import com.sosnowskydevelop.tripmanager.utilities.BUNDLE_KEY_REFUELING_LIST_TRIP_ID
import com.sosnowskydevelop.tripmanager.utilities.REQUEST_KEY_REFUELING_LIST_TRIP_ID
import com.sosnowskydevelop.tripmanager.viewmodels.TripListItemViewModel

class TripAdapter(
    private val fragment: Fragment
) : ListAdapter<Trip, RecyclerView.ViewHolder>(TripDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(binding = ListItemTripBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TripViewHolder).bind(trip = getItem(position))
    }

    fun updateTripList(tripList: List<Trip>) {
        submitList(tripList)
        notifyDataSetChanged()
    }

    inner class TripViewHolder(
        private val binding: ListItemTripBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trip: Trip) {
            binding.viewModel = TripListItemViewModel(trip = trip)
            binding.executePendingBindings()
            binding.container.setOnClickListener {
                fragment.setFragmentResult(requestKey = REQUEST_KEY_REFUELING_LIST_TRIP_ID,
                        result = bundleOf(
                                BUNDLE_KEY_REFUELING_LIST_TRIP_ID to trip.tripId))
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