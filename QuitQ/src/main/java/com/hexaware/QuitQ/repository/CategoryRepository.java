package com.hexaware.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQ.entities.Category;

public interface CategoryRepository extends JpaRepository <Category,Long>{

	static Category findByName(String categoryName) {
		// TODO Auto-generated method stub
		return null;
	}

}
