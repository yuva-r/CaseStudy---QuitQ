package com.hexaware.QuitQ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.QuitQ.entities.CartItem;
import com.hexaware.QuitQ.repository.CartItemRepository;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping
    public CartItem createCartItem(@RequestBody CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public CartItem getCartItem(@PathVariable Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public CartItem updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        CartItem c = cartItemRepository.findById(id).orElse(null);
        if (c != null) {
            c.setProduct(cartItem.getProduct());
            c.setQuantity(cartItem.getQuantity());
            c.setUser(cartItem.getUser());
            return cartItemRepository.save(c);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
    }
}