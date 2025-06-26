package com.hexaware.QuitQ.repository;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.QuitQ.entities.User;







@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String userName);
	public boolean existsByEmail(String email);
	public Optional<User> findByUsernameOrEmail(String userName, String email);
//	public Object findByName(String name);
	public boolean existsByUsername(String username);
}