
package com.hexaware.QuitQ.serviceimpl;

import com.hexaware.QuitQ.dto.CartDTO;
import com.hexaware.QuitQ.entities.Cart;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.repository.CartRepository;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;
	

	/*
	 * @Override public CartDTO createCart(CartDTO cartDTO) { Cart cart = new
	 * Cart(); cart.setQuantity(cartDTO.getQuantity());
	 * 
	 * Optional<User> userOpt = userRepository.findById(cartDTO.getUserId());
	 * Optional<Product> productOpt =
	 * productRepository.findById(cartDTO.getProductId());
	 * 
	 * if (userOpt.isPresent() && productOpt.isPresent()) {
	 * cart.setUser(userOpt.get()); cart.setProduct(productOpt.get()); Cart
	 * savedCart = cartRepository.save(cart);
	 * 
	 * CartDTO result = new CartDTO(); result.setId(savedCart.getId());
	 * result.setQuantity(savedCart.getQuantity());
	 * result.setUserId(savedCart.getUser().getId());
	 * result.setProductId(savedCart.getProduct().getId()); return result; }
	 * 
	 * return null; }
	 */
	@Override
	public CartDTO createCart(CartDTO cartDTO) {
	    Cart cart = new Cart();
	    cart.setQuantity(cartDTO.getQuantity());

	    Optional<User> userOpt = userRepository.findById(cartDTO.getUserId());
	    Optional<Product> productOpt = productRepository.findById(cartDTO.getProductId());

	    if (userOpt.isPresent() && productOpt.isPresent()) {
	        cart.setUser(userOpt.get());
	        cart.setProduct(productOpt.get());
	        Cart savedCart = cartRepository.save(cart);

	        // ✅ Now fully populate the DTO
	        CartDTO result = new CartDTO();
	        result.setId(savedCart.getId());
	        result.setQuantity(savedCart.getQuantity());
	        result.setUserId(savedCart.getUser().getId());
	        result.setProductId(savedCart.getProduct().getId());
	        result.setProductName(savedCart.getProduct().getName());
	        result.setProductPrice(savedCart.getProduct().getPrice());
	        result.setProductImage(savedCart.getProduct().getImageUrl());
	        result.setTotalCost(savedCart.getQuantity() * savedCart.getProduct().getPrice());
	        
	        return result;
	    }

	    return null;
	}
	@Override
	public CartDTO getCartById(Long id) {
		Optional<Cart> cartOpt = cartRepository.findById(id);
		if (cartOpt.isPresent()) {
			Cart cart = cartOpt.get();
			CartDTO cartDTO = new CartDTO();
			cartDTO.setId(cart.getId());
			cartDTO.setQuantity(cart.getQuantity());
			cartDTO.setUserId(cart.getUser().getId());
			cartDTO.setProductId(cart.getProduct().getId());
			return cartDTO;
		}
		return null;
	}

	@Override
	public List<CartDTO> getAllCarts() {
		return cartRepository.findAll().stream().map(cart -> {
			CartDTO cartDTO = new CartDTO();
			cartDTO.setId(cart.getId());
			cartDTO.setQuantity(cart.getQuantity());
			cartDTO.setUserId(cart.getUser().getId());
			cartDTO.setProductId(cart.getProduct().getId());
			return cartDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public CartDTO updateCart(Long id, CartDTO cartDTO) {
		Optional<Cart> cartOpt = cartRepository.findById(id);
		Optional<User> userOpt = userRepository.findById(cartDTO.getUserId());
		Optional<Product> productOpt = productRepository.findById(cartDTO.getProductId());

		if (cartOpt.isPresent() && userOpt.isPresent() && productOpt.isPresent()) {
			Cart cart = cartOpt.get();
			cart.setQuantity(cartDTO.getQuantity());
			cart.setUser(userOpt.get());
			cart.setProduct(productOpt.get());
			Cart updatedCart = cartRepository.save(cart);

			CartDTO result = new CartDTO();
			result.setId(updatedCart.getId());
			result.setQuantity(updatedCart.getQuantity());
			result.setUserId(updatedCart.getUser().getId());
			result.setProductId(updatedCart.getProduct().getId());
			return result;
		}
		return null;
	}

	@Override
	public void deleteCart(Long id) {
		cartRepository.deleteById(id);
	}
	@Override
	public List<CartDTO> getCartByUserId(Long userId) {
	    List<Cart> cartItems = cartRepository.findByUserId(userId);
	    return cartItems.stream().map(cart -> {
	        CartDTO dto = new CartDTO();
	        dto.setId(cart.getId());
	        dto.setQuantity(cart.getQuantity());
	        dto.setUserId(cart.getUser().getId());
	        dto.setProductId(cart.getProduct().getId());
	        dto.setProductName(cart.getProduct().getName());
	        dto.setProductPrice(cart.getProduct().getPrice());
	        dto.setProductImage(cart.getProduct().getImageUrl()); 
	        dto.setTotalCost(cart.getQuantity() * cart.getProduct().getPrice());
	        return dto;
	    }).collect(Collectors.toList());
	}
	@Transactional
	@Override
	public void clearCartByUserId(Long userId) {
	    cartRepository.deleteByUserId(userId);
	}
}
