package com.hexaware.QuitQ.dto;

public class CartDTO {
	private Long id;
	private int quantity;
	private Long userId;
	private Long productId;
	private String productName;
	private double productPrice;
	private double totalCost;
	private String productImage;
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(Long id, int quantity, Long userId, Long productId, String productName, double productPrice,
			double totalCost, String productImage) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.totalCost = totalCost;
		this.productImage = productImage;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	
}