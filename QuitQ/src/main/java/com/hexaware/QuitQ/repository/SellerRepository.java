package com.hexaware.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQ.entities.Seller;

public interface SellerRepository extends JpaRepository<Seller,Long>{

	static Seller findByName(String sellerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
