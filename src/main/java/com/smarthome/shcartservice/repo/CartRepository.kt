package com.smarthome.shcartservice.repo

import com.smarthome.shcartservice.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    fun findCartByUserId(userId: Long): Optional<Cart>
}
