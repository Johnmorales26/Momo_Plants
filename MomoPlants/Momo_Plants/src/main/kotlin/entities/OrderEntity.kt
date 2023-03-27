package entities

import utils.OrderStatus

data class OrderEntity(
    val id: Int = 0,
    var status: OrderStatus = OrderStatus.PENDING,
    var items: MutableList<ItemEntity> = mutableListOf()
)
