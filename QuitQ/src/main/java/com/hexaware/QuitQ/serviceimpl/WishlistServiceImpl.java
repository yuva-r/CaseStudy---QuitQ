package com.hexaware.QuitQ.serviceimpl;

import com.hexaware.QuitQ.dto.WishlistProductDTO;
import com.hexaware.QuitQ.dto.WishlistRequestDTO;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.entities.Wishlist;
import com.hexaware.QuitQ.exceptions.ResourceNotFoundException;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.repository.WishlistRepository;
import com.hexaware.QuitQ.service.WishlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public String addToWishlist(WishlistRequestDTO dto) {
        if (wishlistRepo.existsByUserIdAndProductId(dto.getUserId(), dto.getProductId())) {
            return "Already in wishlist";
        }

        User user = userRepo.findById(dto.getUserId()).orElse(null);
        Product product = productRepo.findById(dto.getProductId()).orElse(null);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);

        wishlistRepo.save(wishlist);
        return "Added to wishlist";
    }

    @Override
    public List<WishlistProductDTO> getWishlistByUserId(Long userId) {
        List<Wishlist> wishlist = wishlistRepo.findByUserId(userId);
        return wishlist.stream()
                .map(w -> new WishlistProductDTO(w.getProduct()))
                .collect(Collectors.toList());
    }
	/*
	 * @Override public String removeFromWishlist(WishlistRequestDTO dto) {
	 * wishlistRepo.deleteByUserIdAndProductId(dto.getUserId(), dto.getProductId());
	 * return "Removed from wishlist"; }
	 */
    @Override
    public String removeFromWishlist(WishlistRequestDTO dto) {
        Wishlist wishlistItem = wishlistRepo.findByUserIdAndProductId(dto.getUserId(), dto.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));
        wishlistRepo.delete(wishlistItem);
        return "Removed from wishlist";
    }
}