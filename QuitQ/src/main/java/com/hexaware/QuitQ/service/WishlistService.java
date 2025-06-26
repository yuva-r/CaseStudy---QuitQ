package com.hexaware.QuitQ.service;

import com.hexaware.QuitQ.dto.WishlistProductDTO;
import com.hexaware.QuitQ.dto.WishlistRequestDTO;

import java.util.List;

public interface WishlistService {
    String addToWishlist(WishlistRequestDTO dto);
    List<WishlistProductDTO> getWishlistByUserId(Long userId);
    String removeFromWishlist(WishlistRequestDTO dto);
}