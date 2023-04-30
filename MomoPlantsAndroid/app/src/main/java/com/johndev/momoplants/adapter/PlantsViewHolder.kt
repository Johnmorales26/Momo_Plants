package com.johndev.momoplants.adapter

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplants.PlantsProvider
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.PlantEntity

class PlantsViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val plantName = view.findViewById<TextView>(R.id.tvPlantName)
    val precio = view.findViewById<TextView>(R.id.tvPrecio)
    val photo = view.findViewById<ImageView>(R.id.ivPlant)

    fun render(plantModel: PlantEntity){
        plantName.text = plantModel.name

    }


}