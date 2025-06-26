package com.hexaware.QuitQ.repository;

import com.hexaware.QuitQ.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
    Optional<Wishlist> findByUserIdAndProductId(Long userId, Long productId);
}