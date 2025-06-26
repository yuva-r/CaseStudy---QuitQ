package com.hexaware.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQ.entities.CartItem;

public interface CartItemRepository extends JpaRepository <CartItem,Long>{

}
