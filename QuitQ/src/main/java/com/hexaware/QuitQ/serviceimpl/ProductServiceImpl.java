
package com.hexaware.QuitQ.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQ.dto.ProductDTO;
import com.hexaware.QuitQ.entities.Category;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.entities.Seller;
import com.hexaware.QuitQ.repository.CategoryRepository;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.SellerRepository;
import com.hexaware.QuitQ.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SellerRepository sellerRepository;

	private ProductDTO mapToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		dto.setImageUrl(product.getImageUrl());
	
		return dto;
	}

	private Product mapToEntity(ProductDTO dto) {
		Product product = new Product();
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setStock(dto.getStock());
		product.setImageUrl(dto.getImageUrl());
		
		return product;
	}

	@Override
	public ProductDTO createProduct(ProductDTO dto) {
		return mapToDTO(productRepository.save(mapToEntity(dto)));
	}

	@Override
	public ProductDTO getProductById(Long id) {
	    Product product = productRepository.findById(id).orElse(null);
	    if (product == null) return null;

	    String categoryName = product.getCategory() != null ? product.getCategory().getName() : "Unknown Category";

	    String sellerName = "Unknown Seller";
	    String storeName = "";
	    String gstNumber = "";

	    if (product.getSeller() != null) {
	        sellerName = product.getSeller().getName();
	        storeName = product.getSeller().getStoreName();
	        gstNumber = product.getSeller().getGstNumber();
	    }

	    return new ProductDTO(
	        product.getId(),
	        product.getName(),
	        product.getDescription(),
	        product.getPrice(),
	        product.getStock(),
	        product.getImageUrl(),
	        categoryName,
	        sellerName,
	        storeName,
	        gstNumber
	    );
	}
	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		dto.setId(id);
		return mapToDTO(productRepository.save(mapToEntity(dto)));
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

}
