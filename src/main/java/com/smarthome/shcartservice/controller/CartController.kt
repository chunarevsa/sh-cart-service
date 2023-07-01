package com.smarthome.shcartservice.controller

import com.smarthome.shcartservice.dto.CreateCartRequest
import com.smarthome.shcartservice.entity.Cart
import com.smarthome.shcartservice.service.CartService
import com.smarthome.shcartservice.util.HeaderUtil
import com.smarthome.shcartservice.util.ResponseUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/cart")
class CartController(
    private val cartService: CartService,
    @Value("\${spring.application.name}")
    private val applicationName: String
) {
    private val log: Logger = LoggerFactory.getLogger(CartController::class.java)

    private val ENTITY_NAME = "cart"

    @GetMapping("/{userId}")
    fun getCartByUserId(@PathVariable userId: Long): ResponseEntity<Cart> {
        log.debug("REST request to get $ENTITY_NAME : {}", userId)
        return ResponseUtil.wrapOrNotFound(cartService.getCartByUserId(userId))
    }

    // TODO add @Valid
    @PostMapping("/create")
    fun createOrUpdateCart(@RequestBody req: CreateCartRequest): ResponseEntity<Cart> {
        log.debug("REST request to create or update $ENTITY_NAME : {}", req)
        val cart = cartService.createOrUpdateCart(req)
        return ResponseEntity.created(URI("/api/cart/${cart.id}"))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName, false, ENTITY_NAME, cart.id.toString()
                )
            )
            .body(cart)
    }

    @PostMapping("/{userId}/delete")
    fun delete(@PathVariable userId: Long): ResponseEntity<Void> {
        log.debug("REST request to deactivate cart : {}", userId)
        cartService.deleteCartByUserId(userId)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeletionAlert(
                    applicationName, false, ENTITY_NAME, userId.toString()
                )
            ).build()
    }
    
}