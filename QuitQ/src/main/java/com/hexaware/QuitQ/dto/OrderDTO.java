package com.hexaware.QuitQ.dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {
	private Long id;
	private double totalAmount;
	private Date orderDate;
	private String status;
	private Long userId;
	private List<Long> itemIds;
	private Long shippingAddressId;
	private List<OrderItemDTO> orderItems;
	private ShippingAddressDTO shippingAddress;
	private PaymentDTO payment;
	private String userName;
	private String userEmail;
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDTO(Long id, double totalAmount, Date orderDate, String status, Long userId, List<Long> itemIds,
			Long shippingAddressId, List<OrderItemDTO> orderItems, ShippingAddressDTO shippingAddress,
			PaymentDTO payment, String userName, String userEmail) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.orderDate = orderDate;
		this.status = status;
		this.userId = userId;
		this.itemIds = itemIds;
		this.shippingAddressId = shippingAddressId;
		this.orderItems = orderItems;
		this.shippingAddress = shippingAddress;
		this.payment = payment;
		this.userName = userName;
		this.userEmail = userEmail;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Long> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}
	public Long getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
	public ShippingAddressDTO getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public PaymentDTO getPayment() {
		return payment;
	}
	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	
}