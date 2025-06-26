package com.hexaware.QuitQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQ.entities.Product;


public interface ProductRepository extends JpaRepository <Product, Long> {
	List<Product> findBySellerId(Long sellerId);
}
