package com.hexaware.QuitQ.controller;

import com.hexaware.QuitQ.dto.WishlistProductDTO;
import com.hexaware.QuitQ.dto.WishlistRequestDTO;
import com.hexaware.QuitQ.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin("*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistRequestDTO dto) {
        String response = wishlistService.addToWishlist(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistProductDTO>> getWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(@RequestBody WishlistRequestDTO dto) {
        String response = wishlistService.removeFromWishlist(dto);
        return ResponseEntity.ok(response);
    }
}