package com.hexaware.QuitQ.dto;

public class CartItemDTO {
	private Long id;
	private int quantity;
	private Long userId;
	private Long productId;
	
	public CartItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartItemDTO(Long id, int quantity, Long userId, Long productId) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.userId = userId;
		this.productId = productId;
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
	
}