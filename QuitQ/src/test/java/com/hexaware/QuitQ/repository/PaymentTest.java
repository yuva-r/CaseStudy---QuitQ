package com.hexaware.QuitQ.repository;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hexaware.QuitQ.entities.Order;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTest {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private double amount;
		private String paymentMethod;
		private String paymentStatus;
		private LocalDateTime paymentDate;
		@OneToOne
		@JsonIgnore
	    @JoinColumn(name = "order_id", unique = true)
	    private Order order;
}