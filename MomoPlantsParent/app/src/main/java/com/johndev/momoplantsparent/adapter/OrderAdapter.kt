package com.johndev.momoplantsparent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.dataAccess.OnOrderListener
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.databinding.ItemOrderBinding

class OrderAdapter(
    private val orderList: MutableList<OrderEntity>,
    private val listener: OnOrderListener
) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val arrayValues: Array<String> by lazy { context.resources.getStringArray(R.array.status_value) }
    private val arrayKey: Array<Int> by lazy { context.resources.getIntArray(R.array.status_key).toTypedArray() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderEntity = orderList[position]
        holder.apply {
            setListener(orderEntity)
            with(binding) {
                tvId.text = context.getString(R.string.order_id, orderEntity.id)
                var names = ""
                orderEntity.products.forEach { names += "${it.value.name}, " }
                tvProductNames.text = names.dropLast(2)
                tvTotalPrice.text = context.getString(R.string.product_full_cart, orderEntity.totalPrice)
                val index = arrayKey.indexOf(orderEntity.status)
                val statusStr = if (index != -1) arrayValues[index] else context.getString(R.string.order_status_unknown)
                val statusAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayValues)
                actvStatus.setAdapter(statusAdapter)
                if (index != -1) {
                    actvStatus.setText(arrayValues[index], false)
                } else {
                    actvStatus.setText(context.getString(R.string.order_status_unknown), false)
                }
            }
        }
    }

    override fun getItemCount(): Int = orderList.size

    fun add(orderEntity: OrderEntity) {
        if (!orderList.contains(orderEntity)) {
            orderList.add(orderEntity)
            notifyItemInserted(orderList.size - 1)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemOrderBinding.bind(view)

        fun setListener(orderEntity: OrderEntity) {
            binding.let {
                binding.actvStatus.setOnItemClickListener { _, _, position, _ ->
                    orderEntity.status = arrayKey[position]
                    listener.onStatusChange(orderEntity)
                }
                it.chipChat.setOnClickListener { listener.onStartChat(orderEntity) }
            }
        }
    }

}