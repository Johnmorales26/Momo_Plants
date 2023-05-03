package com.johndev.momoplants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.johndev.momoplants.common.dataAccess.OnClickListener
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.setupImage
import java.lang.StringBuilder

class PlantAdapter(private val PlantList: List<PlantEntity>, private val listener: OnClickListener): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = PlantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = PlantList[position]
        holder.render(item)
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private val tvPlantName: MaterialTextView = view.findViewById(R.id.tvPlantName)
        private val tvPrice: MaterialTextView = view.findViewById(R.id.tvPrice)
        private val imgPlant: ImageView = view.findViewById(R.id.imgPlant)
        private val tvDescription: MaterialTextView = view.findViewById(R.id.tvDescription)
        private val btnDetails: MaterialButton = view.findViewById(R.id.btnDetails)

        fun render(plantModel: PlantEntity){
            tvPlantName.text = plantModel.name
            tvPrice.text = StringBuilder().append("$").append(plantModel.price.toString().trim()).toString().trim()
            tvDescription.text = plantModel.description.trim()
            setupImage(imgCover = imgPlant, imgRes = plantModel.image)
            btnDetails.setOnClickListener {
                listener.onClicListener(plantModel)
            }
        }


    }

}