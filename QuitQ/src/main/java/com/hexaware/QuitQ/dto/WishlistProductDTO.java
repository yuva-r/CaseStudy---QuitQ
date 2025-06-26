package com.hexaware.QuitQ.dto;

import com.hexaware.QuitQ.entities.Product;

public class WishlistProductDTO {

    private Long id;
    private String name;
    private String imageUrl;
    

    public WishlistProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WishlistProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}