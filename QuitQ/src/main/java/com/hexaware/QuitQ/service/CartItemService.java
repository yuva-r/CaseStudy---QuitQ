
  package com.hexaware.QuitQ.service;
  
  import java.util.List;
  
  import com.hexaware.QuitQ.dto.CartItemDTO;
  
  public interface CartItemService { CartItemDTO createCartItem(CartItemDTO
  cartItemDTO); CartItemDTO getCartItemById(Long id); List<CartItemDTO>
  getAllCartItems(); CartItemDTO updateCartItem(Long id, CartItemDTO
  cartItemDTO); void deleteCartItem(Long id); }
 