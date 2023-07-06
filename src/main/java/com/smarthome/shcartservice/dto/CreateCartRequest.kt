package com.smarthome.shcartservice.dto

data class CreateCartRequest(
    val userId: Long,
    val itemUnits: MutableList<ItemUnitDto>?
)
