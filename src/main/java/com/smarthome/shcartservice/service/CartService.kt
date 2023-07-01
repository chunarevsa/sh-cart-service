package com.smarthome.shcartservice.service

import com.smarthome.shcartservice.dto.CreateCartRequest
import com.smarthome.shcartservice.entity.Cart
import com.smarthome.shcartservice.entity.ItemUnit
import com.smarthome.shcartservice.repo.CartRepository
import com.smarthome.shuserservice.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(
    private val cartRepository: CartRepository
) {

    fun getCartByUserId(userId: Long): Optional<Cart> = cartRepository.findCartByUserId(userId)

    fun createOrUpdateCart(req: CreateCartRequest): Cart =
        cartRepository.save(getCartByUserId(req.userId).map { oldCart ->
            req.itemsUnit.forEach { requestItem ->
                val oldItem = oldCart.items.find { it.sku == requestItem.sku }
                if (oldItem != null) {
                    oldItem.amount.plus(requestItem.amount)
                } else oldCart.items.add(ItemUnit().apply {
                    this.amount = requestItem.amount
                    this.sku = requestItem.sku
                    this.cart = oldCart
                })
            }
            return@map cartRepository.save(oldCart)
        }.orElseGet {
            return@orElseGet cartRepository.save(
                Cart().apply {
                    this.userId = req.userId
                    this.items = req.itemsUnit
                }
            )
        })

    fun deleteCartByUserId(userId: Long) {
        val cart = findCartByUserId(userId)
        cartRepository.deleteByUserId(cart.userId)
    }

    private fun findCartByUserId(userId: Long): Cart {
        return cartRepository.findCartByUserId(userId).map { it }.orElseThrow {
            NotFoundException("cart", userId.toString())
        }
    }
}
