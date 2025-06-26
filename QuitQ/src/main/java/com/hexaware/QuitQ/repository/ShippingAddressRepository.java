package com.hexaware.QuitQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQ.entities.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long>{
	List<ShippingAddress> findByUserId(Long userId);
}
