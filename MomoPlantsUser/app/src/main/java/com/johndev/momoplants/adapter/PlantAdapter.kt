package com.johndev.momoplants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johndev.momoplants.R
import com.johndev.momoplants.databinding.ItemPlantBinding
import com.johndev.momoplants.common.dataAccess.OnProductListener
import com.johndev.momoplants.common.entities.PlantEntity
import java.lang.StringBuilder

class PlantAdapter(
    private val plantList: MutableList<PlantEntity>,
    private val listener: OnProductListener
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plantEntity = plantList[position]
        with(holder) {
            setListener(plantEntity)
            binding.let {
                it.tvPlantName.text = plantEntity.name?.trim()
                it.tvPrice.text =
                    StringBuilder().append("$").append(plantEntity.price.toString().trim())
                        .toString()
                Glide
                    .with(context)
                    .load(plantEntity.imageUrl)
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_broken_image)
                    .into(it.imgPlant)
            }
        }
    }

    fun add(plantEntity: PlantEntity) {
        if (!plantList.contains(plantEntity)) {
            plantList.add(plantEntity)
            notifyItemInserted(plantList.size - 1)
        }
    }

    fun update(plantEntity: PlantEntity) {
        val index = plantList.indexOf(plantEntity)
        if (index != -1) {
            plantList[index] = plantEntity
            notifyItemChanged(index)
        }
    }

    fun delete(plantEntity: PlantEntity) {
        val index = plantList.indexOf(plantEntity)
        if (index != -1) {
            plantList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlantBinding.bind(view)

        fun setListener(plantEntity: PlantEntity) {
            binding.cardItem.setOnClickListener { listener.onClick(plantEntity) }
        }
    }

}