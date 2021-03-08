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
import com.sosnowskydevelop.tripmanager.data.Refueling
import com.sosnowskydevelop.tripmanager.databinding.ListItemRefuelingBinding
import com.sosnowskydevelop.tripmanager.utilities.BUNDLE_KEY_REFUELING
import com.sosnowskydevelop.tripmanager.utilities.REQUEST_KEY_REFUELING
import com.sosnowskydevelop.tripmanager.viewmodels.RefuelingListItemViewModel

class RefuelingAdapter(
    private val fragment: Fragment
) : ListAdapter<Refueling, RecyclerView.ViewHolder>(RefuelingDiffCallback()) {

    private lateinit var refuelingList: List<Refueling>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefuelingViewHolder {
        return RefuelingViewHolder(ListItemRefuelingBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RefuelingViewHolder).bind(getItem(position))
    }

    fun updateRefuelingList(refuelingList: List<Refueling>) {
        this.refuelingList = refuelingList
        submitList(refuelingList)
        notifyDataSetChanged()
    }

    inner class RefuelingViewHolder(
        private val binding: ListItemRefuelingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Refueling) {

            binding.viewModel = RefuelingListItemViewModel(item)

            binding.executePendingBindings()

            binding.container.setOnClickListener {

                val result = item.refuelingId

                fragment.setFragmentResult(
                        REQUEST_KEY_REFUELING, bundleOf(BUNDLE_KEY_REFUELING to result))

                fragment.findNavController()
                        .navigate(R.id.action_refuelingListFragment_to_refuelingInfoFragment)
            }
        }
    }
}

private class RefuelingDiffCallback : DiffUtil.ItemCallback<Refueling>() {

    override fun areItemsTheSame(oldItem: Refueling, newItem: Refueling): Boolean {
        return oldItem.refuelingId == newItem.refuelingId
    }

    override fun areContentsTheSame(oldItem: Refueling, newItem: Refueling): Boolean {
        return oldItem == newItem
    }
}