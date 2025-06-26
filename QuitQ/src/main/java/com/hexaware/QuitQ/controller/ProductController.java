package com.hexaware.QuitQ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hexaware.QuitQ.dto.ProductDTO;
import com.hexaware.QuitQ.entities.Category;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.entities.Seller;
import com.hexaware.QuitQ.repository.CategoryRepository;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.SellerRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("http://localhost:5173")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@PostMapping
	 @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
	public Product create(@RequestBody ProductDTO dto) {
		Product product = new Product();
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setStock(dto.getStock());
		product.setImageUrl(dto.getImageUrl());

		// Set Category
		if (dto.getCategoryId() == null) {
			throw new RuntimeException("Category ID is required");
		}
		Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
		if (category != null) {
			product.setCategory(category);
		} else {
			throw new RuntimeException("Category ID " + dto.getCategoryId() + " not found");
		}

		// Set Seller
		if (dto.getSellerId() == null) {
			throw new RuntimeException("Seller ID is required");
		}
		Seller seller = sellerRepository.findById(dto.getSellerId()).orElse(null);
		if (seller != null) {
			product.setSeller(seller);
		} else {
			throw new RuntimeException("Seller ID " + dto.getSellerId() + " not found");
		}

		return productRepository.save(product);
	}

	@GetMapping
	public List<ProductDTO> all() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ProductDTO get(@PathVariable Long id) {
		Product product = productRepository.findById(id).orElse(null);
		if (product == null)
			return null;

		String categoryName = product.getCategory() != null ? product.getCategory().getName() : "Unknown Category";
		String sellerName = product.getSeller() != null ? product.getSeller().getName() : "Unknown Seller";
		String storeName = product.getSeller() != null ? product.getSeller().getStoreName() : "";
		String gstNumber = product.getSeller() != null ? product.getSeller().getGstNumber() : "";

		return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
				product.getStock(), product.getImageUrl(), categoryName, sellerName, storeName, gstNumber);
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @RequestBody Product p) {
		Product existing = productRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setCategory(p.getCategory());
			existing.setName(p.getName());
			existing.setDescription(p.getDescription());
			existing.setPrice(p.getPrice());
			existing.setStock(p.getStock());
			existing.setImageUrl(p.getImageUrl());
			existing.setSeller(p.getSeller());
			return productRepository.save(existing);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	 @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
	public void delete(@PathVariable Long id) {
		productRepository.deleteById(id);
	}

	@GetMapping("/seller/{sellerId}")
	public List<ProductDTO> getProductsBySeller(@PathVariable Long sellerId) {
		List<Product> products = productRepository.findBySellerId(sellerId);
		return products.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		dto.setImageUrl(product.getImageUrl());
		if (product.getCategory() != null) {
			dto.setCategoryName(product.getCategory().getName());
		} else {
			dto.setCategoryName("Unknown Category");
		}
		if (product.getSeller() != null) {
			dto.setSellerName(product.getSeller().getName());
			dto.setStoreName(product.getSeller().getStoreName());
			dto.setGstNumber(product.getSeller().getGstNumber());
		}
		return dto;
	}
}