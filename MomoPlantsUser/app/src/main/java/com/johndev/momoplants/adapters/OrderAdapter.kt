package com.johndev.momoplants.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplants.R
import com.johndev.momoplants.common.dataAccess.OnOrderListener
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.databinding.ItemOrderBinding

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
                tvStatus.text = context.getString(R.string.order_status, statusStr)
            }
        }
    }

    override fun getItemCount(): Int = orderList.size

    fun add(orderEntity: OrderEntity) {
        orderList.add(orderEntity)
        notifyItemInserted(orderList.size - 1)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemOrderBinding.bind(view)

        fun setListener(orderEntity: OrderEntity) {
            binding.let {
                it.btnTrack.setOnClickListener { listener.onTrack(orderEntity) }
                it.chipChat.setOnClickListener { listener.onStartChat(orderEntity) }
            }
        }
    }

}