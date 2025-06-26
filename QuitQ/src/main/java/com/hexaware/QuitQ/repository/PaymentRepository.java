package com.hexaware.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQ.entities.Order;
import com.hexaware.QuitQ.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{
	Payment findByOrder(Order order);

	Payment findByOrderId(Long id);
}
