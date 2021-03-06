package com.sosnowskydevelop.tripmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sosnowskydevelop.tripmanager.R
import com.sosnowskydevelop.tripmanager.data.Car

class CarAdapter (private val cars: ArrayList<Car>) :
        RecyclerView.Adapter<CarAdapter.CarsViewHolder>() {

    class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.car_short_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_car, parent, false)
        return CarsViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        holder.textView.text = cars[position]._shortName
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}