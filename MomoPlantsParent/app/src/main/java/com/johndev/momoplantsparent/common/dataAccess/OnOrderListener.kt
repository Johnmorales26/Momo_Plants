package com.johndev.momoplantsparent.common.dataAccess

import com.johndev.momoplantsparent.common.entities.OrderEntity

interface OnOrderListener {

    fun onStartChat(orderEntity: OrderEntity)
    fun onStatusChange(orderEntity: OrderEntity)

}