package com.smarthome.shcartservice.repo;

import com.smarthome.shcartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByUserId(Long userId);

    void deleteCartByUserId(Long userId);

}
