package com.smarthome.shcartservice.dto

import com.smarthome.shcartservice.entity.ItemUnit

data class CreateCartRequest(
    val userId: Long,
    val itemsUnit: MutableSet<ItemUnit>
)
