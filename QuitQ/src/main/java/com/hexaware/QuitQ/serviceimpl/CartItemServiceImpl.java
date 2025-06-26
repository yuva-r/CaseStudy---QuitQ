
package com.hexaware.QuitQ.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQ.dto.CartItemDTO;
import com.hexaware.QuitQ.entities.CartItem;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.repository.CartItemRepository;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.CartItemService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(cartItemDTO.getQuantity());

		if (cartItemDTO.getUserId() != null) {
			Optional<User> user = userRepository.findById(cartItemDTO.getUserId());
			user.ifPresent(cartItem::setUser);
		}

		if (cartItemDTO.getProductId() != null) {
			Optional<Product> product = productRepository.findById(cartItemDTO.getProductId());
			product.ifPresent(cartItem::setProduct);
		}

		CartItem saved = cartItemRepository.save(cartItem);
		return mapToDTO(saved);
	}

	@Override
	public CartItemDTO getCartItemById(Long id) {
		Optional<CartItem> cartItemOpt = cartItemRepository.findById(id);
		return cartItemOpt.map(this::mapToDTO).orElse(null);//converts to dto and returns it
	}
   //converts each one to a dto using stream and returns a list
	@Override
	public List<CartItemDTO> getAllCartItems() {
		return cartItemRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
		Optional<CartItem> existing = cartItemRepository.findById(id);
		if (existing.isPresent()) {
			CartItem cartItem = existing.get();
			cartItem.setQuantity(cartItemDTO.getQuantity());

			if (cartItemDTO.getUserId() != null) {
				userRepository.findById(cartItemDTO.getUserId()).ifPresent(cartItem::setUser);
			}

			if (cartItemDTO.getProductId() != null) {
				productRepository.findById(cartItemDTO.getProductId()).ifPresent(cartItem::setProduct);
			}

			return mapToDTO(cartItemRepository.save(cartItem));
		}
		return null;
	}

	@Override
	public void deleteCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}
    // converts entity to dto an dextracts id
	private CartItemDTO mapToDTO(CartItem cartItem) {
		CartItemDTO dto = new CartItemDTO();
		dto.setId(cartItem.getId());
		dto.setQuantity(cartItem.getQuantity());
		dto.setUserId(cartItem.getUser() != null ? cartItem.getUser().getId() : null);
		dto.setProductId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null);
		return dto;
	}

}
