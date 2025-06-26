package com.hexaware.QuitQ.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    

    private String storeName;

    private String gstNumber;

    @OneToMany(mappedBy = "seller",cascade=CascadeType.ALL,orphanRemoval=true)@JsonIgnore
    private List<Product> products;

	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seller(Long id, String name, User user, String storeName, String gstNumber, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.storeName = storeName;
		this.gstNumber = gstNumber;
		this.products = products;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
    

	

	
    
    
}
