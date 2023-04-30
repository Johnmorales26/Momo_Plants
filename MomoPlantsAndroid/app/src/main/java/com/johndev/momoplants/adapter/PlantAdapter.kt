package com.johndev.momoplants.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.PlantEntity

class PlantAdapter(private val PlantList: List<PlantEntity>): RecyclerView.Adapter<PlantsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlantsViewHolder(layoutInflater.inflate(R.layout.item_plant,parent,false))
    }

    override fun getItemCount(): Int = PlantList.size

    override fun onBindViewHolder(holder: PlantsViewHolder, position: Int) {
        val item = PlantList[position]
        holder.render(item)
    }
}