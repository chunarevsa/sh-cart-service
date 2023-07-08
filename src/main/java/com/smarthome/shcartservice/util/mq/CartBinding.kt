package com.smarthome.shcartservice.util.mq

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface CartBinding {

    companion object {
        const val INPUT_CHANNEL: String = "cartInputChannel"
    }

    @Input(INPUT_CHANNEL)
    fun cartInputChannel(): MessageChannel

}