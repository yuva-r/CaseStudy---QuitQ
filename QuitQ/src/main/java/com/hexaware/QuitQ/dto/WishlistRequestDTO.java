package com.hexaware.QuitQ.dto;

public class WishlistRequestDTO {
    private Long userId;
    private Long productId;
    

    public WishlistRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WishlistRequestDTO(Long userId, Long productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}
	public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}