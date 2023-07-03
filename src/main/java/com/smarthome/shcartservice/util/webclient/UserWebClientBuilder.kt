package com.smarthome.shcartservice.util.webclient

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class UserWebClientBuilder {

    companion object {
        const val baseUrl = "http://localhost:8765/api/v1/user/"
    }

    fun userExists(userId: Long): Boolean {
        return try {
            WebClient.create(baseUrl)
                .post()
                .uri("isExists")
                .bodyValue(userId)
                .retrieve()
                .bodyToFlux(Boolean::class.java)
                .blockFirst() ?: false

        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            return false
        }
    }
}