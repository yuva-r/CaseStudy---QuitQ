package com.hexaware.QuitQ.dto;

public class OrderItemDTO {
	private Long id;
	private int quantity;
	private double price;
	private Long orderId;
	private Long productId;
	private String productName;
	private String productImageUrl;
	public OrderItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderItemDTO(Long id, int quantity, double price, Long orderId, Long productId, String productName,
			String productImageUrl) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.productImageUrl = productImageUrl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	
	
}