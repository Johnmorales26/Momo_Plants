package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.OrderEntity

interface OnOrderListener {

    fun onTrack(orderEntity: OrderEntity)
    fun onStartChat(orderEntity: OrderEntity)

}