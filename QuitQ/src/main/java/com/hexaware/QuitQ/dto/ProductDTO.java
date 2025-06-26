package com.hexaware.QuitQ.dto;

public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private double price;
	private int stock;
	private String imageUrl;
	private Long categoryId; 
	private String categoryName;// Changed from categoryName
	private String sellerName;
	private String storeName;
	private String gstNumber;
	private Long sellerId;

	// Default constructor
	public ProductDTO() {
	}
    
	
	public ProductDTO(Long id, String name, String description, double price, int stock, String imageUrl,
			String categoryName, String sellerName, String storeName, String gstNumber) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.imageUrl = imageUrl;
		this.categoryName = categoryName;
		this.sellerName = sellerName;
		this.storeName = storeName;
		this.gstNumber = gstNumber;
	}


	public ProductDTO(Long id, String name, String description, double price, int stock, String imageUrl,
			Long categoryId, String categoryName, String sellerName, String storeName, String gstNumber,
			Long sellerId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.sellerName = sellerName;
		this.storeName = storeName;
		this.gstNumber = gstNumber;
		this.sellerId = sellerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	
}