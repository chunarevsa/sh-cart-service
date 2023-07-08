package com.smarthome.shcartservice.util.mq.func

import com.smarthome.shcartservice.dto.CreateCartRequest
import com.smarthome.shcartservice.service.CartService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
open class MessageFunc(
    private val cartService: CartService
) {

    @Bean
    open fun newUserActionConsume(): Consumer<Message<CreateCartRequest>> =
        Consumer { cartService.createOrUpdateCart(it.payload) }

}