package com.smarthome.shcartservice.util.mq

import com.smarthome.shcartservice.dto.CreateCartRequest
import com.smarthome.shcartservice.service.CartService
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
@EnableBinding(CartBinding::class)
open class MessageConsumer(
    private val cartService: CartService
) {
    @StreamListener(target = CartBinding.INPUT_CHANNEL)
    fun createCart(req: CreateCartRequest) {
        cartService.createOrUpdateCart(req)
    }

}