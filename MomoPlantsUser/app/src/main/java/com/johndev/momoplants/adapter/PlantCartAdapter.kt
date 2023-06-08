package com.johndev.momoplants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.johndev.momoplants.R
import com.johndev.momoplants.common.dataAccess.OnCartListener
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.databinding.ItemPlantCartBinding

class PlantCartAdapter(
    private val plantList: MutableList<PlantEntity>,
    private val listener: OnCartListener
) : RecyclerView.Adapter<PlantCartAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_plant_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantList[position]
        holder.let {
            it.setListener(plant)
            with(it.binding) {
                tvName.text = plant.name?.trim()
                tvPrice.text = plant.price.toString().trim()
                tvQuantity.text = plant.quantity.toString().trim()
                Glide
                    .with(context)
                    .load(plant.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_access_time)
                    .error(R.drawable.ic_broken_image)
                    .centerCrop()
                    .circleCrop()
                    .into(imgPlant)
                if (plant.quantity == 1) {
                    ibSub.setImageResource(R.drawable.ic_delete)
                } else {
                    ibSub.setImageResource(R.drawable.ic_remove_circle)
                }
            }
        }

    }

    override fun getItemCount(): Int = plantList.size

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

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        plantList.forEach {
            totalPrice += it.price * it.quantity
        }
        return totalPrice
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlantCartBinding.bind(view)

        fun setListener(plantEntity: PlantEntity) {
            binding.ibSum.setOnClickListener {
                listener.incrementQuantity(plantEntity)
            }
            binding.ibSub.setOnClickListener {
                listener.decrementQuantity(plantEntity)
            }
        }
    }

}