package com.hexaware.QuitQ.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.QuitQ.dto.CartDTO;
import com.hexaware.QuitQ.entities.Cart;
import com.hexaware.QuitQ.repository.CartRepository;
import com.hexaware.QuitQ.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("http://localhost:5173")
public class CartController {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartService cartService;
	

	@PostMapping
	public CartDTO addToCart(@RequestBody CartDTO cartDTO) {
		return cartService.createCart(cartDTO);
	}

	@GetMapping
	public List<Cart> getAllCartItems() {
		return cartRepository.findAll();
	}

	@PutMapping("/{id}")
	public Cart updateCart(@PathVariable Long id, @RequestBody Cart c) {
		Cart cart = cartRepository.findById(id).orElse(null);
		if (cart != null) {
			cart.setUser(c.getUser());
			cart.setProduct(c.getProduct());
			cart.setQuantity(c.getQuantity());
			return cartRepository.save(cart);
		}
		return null;
	}
	@GetMapping("/user/{userId}")
	public List<CartDTO> getCartByUser(@PathVariable Long userId) {
	    return cartService.getCartByUserId(userId);
	}

	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable Long id) {
		cartRepository.deleteById(id);
	}
	@DeleteMapping("/clear/{userId}")
	public void clearCart(@PathVariable Long userId) {
	    cartService.clearCartByUserId(userId);
	}
}