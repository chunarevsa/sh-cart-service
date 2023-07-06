package com.smarthome.shcartservice.service

import com.smarthome.shcartservice.dto.CreateCartRequest
import com.smarthome.shcartservice.entity.Cart
import com.smarthome.shcartservice.entity.CartItemUnit
import com.smarthome.shcartservice.repo.CartRepository
import com.smarthome.shcartservice.exception.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(
    private val cartRepository: CartRepository
) {
    private val log: Logger = LoggerFactory.getLogger(CartService::class.java)

    fun getCartByUserId(userId: Long): Optional<Cart> = cartRepository.findCartByUserId(userId)

    fun createOrUpdateCart(req: CreateCartRequest): Cart =
        cartRepository.save(getCartByUserId(req.userId).map { oldCart ->
            req.itemUnits?.forEach { requestItem ->
                val oldItem = oldCart.items.find { it.sku == requestItem.sku }
                if (oldItem != null) {
                    oldItem.amount.plus(requestItem.amount)
                } else oldCart.items.add(CartItemUnit().apply {
                    this.amount = requestItem.amount
                    this.sku = requestItem.sku
                    this.cart = oldCart
                })
            }
            return@map cartRepository.save(oldCart)
        }.orElseGet
        {
            return@orElseGet cartRepository.save(
                Cart().apply {
                    this.userId = req.userId
                    req.itemUnits?.let { this.items = it.map { itemUnitDto ->
                        CartItemUnit(
                            itemUnitDto.sku,
                            itemUnitDto.amount
                        )
                    }}
                }
            )
        })


    fun deleteCartByUserId(userId: Long) {
        val cart = findCartByUserId(userId)
        cartRepository.delete(cart)
        log.info("Cart with userId:$userId is deleted")
    }

    private fun findCartByUserId(userId: Long): Cart {
        return cartRepository.findCartByUserId(userId).map { it }.orElseThrow {
            NotFoundException("cart", userId.toString())
        }
    }
}
