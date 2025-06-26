package com.hexaware.QuitQ.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.QuitQ.entities.Role;





@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String string);
	
}
