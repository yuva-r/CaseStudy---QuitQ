
package com.hexaware.QuitQ.service;

import java.util.List;

import com.hexaware.QuitQ.dto.CartDTO;

public interface CartService {
	CartDTO createCart(CartDTO cartDTO);

	CartDTO getCartById(Long id);

	List<CartDTO> getAllCarts();

	CartDTO updateCart(Long id, CartDTO cartDTO);

	void deleteCart(Long id);

	List<CartDTO> getCartByUserId(Long userId);

	void clearCartByUserId(Long userId);
}
