package com.hexaware.QuitQ.dto;

public class SellerDTO {
	private Long id;
	private Long userId;
	private String storeName;
	private String gstNumber;
	
	public SellerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SellerDTO(Long id, Long userId, String storeName, String gstNumber) {
		super();
		this.id = id;
		this.userId = userId;
		this.storeName = storeName;
		this.gstNumber = gstNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	
}
