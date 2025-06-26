package com.hexaware.QuitQ.dto;

import java.time.LocalDateTime;

public class PaymentDTO {
	private Long id;
	private double amount;
	private String paymentMethod;
	private String paymentStatus;
	private LocalDateTime paymentDate;
	private Long orderId;
	private Long userId;
	public PaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentDTO(Long id, double amount, String paymentMethod, String paymentStatus, LocalDateTime paymentDate,
			Long orderId, Long userId) {
		super();
		this.id = id;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
		this.orderId = orderId;
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}