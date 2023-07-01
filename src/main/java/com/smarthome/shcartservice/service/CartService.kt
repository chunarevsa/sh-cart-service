package com.smarthome.shcartservice.service

import com.smarthome.shcartservice.dto.CreateCartRequest
import com.smarthome.shcartservice.entity.Cart
import com.smarthome.shcartservice.repo.CartRepository
import com.smarthome.shuserservice.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(
    private val cartRepository: CartRepository
) {

    fun getCartByUserId(userId: Long): Optional<Cart> = cartRepository.findCartByUserId(userId)

    fun createOrUpdateCart(req: CreateCartRequest): Cart {
        val cart = getCartByUserId(req.userId).map {
            it
        }.orElseGet {
            return@orElseGet cartRepository.save(
                Cart().apply {
                    this.userId = req.userId
                    this.items = req.itemsUnit
                }
            )
        }

    }
    cart.addRole(roleService.getRoleByRoleName(ERoleName.ROLE_USER))
    return cartRepository.save(cart)
}

fun updateCart(cartId: Long, req: UpdateCartRequest): Cart {
    val cart = findCart(cartId)
    req.firstName.let { cart.profile.firstName = it }
    req.lastName.let { cart.profile.lastName = it }
    req.password.let { cart.password = it }

    return cartRepository.save(cart)
}

fun deactivateCart(cartId: Long) {
    val cart = findCart(cartId)
    cart.isActive = false
    // TODO: add deactivating items and order, delete cart

    cartRepository.save(cart)
}

private fun findCart(userId: Long): Cart {
    return cartRepository.findCartByUserId(userId).map { it }.orElseThrow {
        NotFoundException("cart", userId.toString())
    }
}
}
