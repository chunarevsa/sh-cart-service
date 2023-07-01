package com.smarthome.shcartservice.controller

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
        val cart = cartService.createCart(req)
        return ResponseEntity.created(URI("/api/cart/${cart.id}"))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName, false, ENTITY_NAME, cart.id.toString()
                )
            )
            .body(cart)
    }

    @PostMapping("/{id}/update")
    fun updateCart(@PathVariable id: Long, @RequestBody req: UpdateCartRequest): ResponseEntity<Cart> {
        log.debug("REST request to update $ENTITY_NAME : {}", req)
        val cart = cartService.updateCart(id, req)
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName, false, ENTITY_NAME, cart.id.toString()
                )
            )
            .body(cart)
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to deactivate cart : {}", id)
        cartService.deleteCart(id)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeletionAlert(
                    applicationName, false, ENTITY_NAME, id.toString()
                )
            ).build()
    }
    
}