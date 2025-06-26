
package com.hexaware.QuitQ.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.QuitQ.dto.SellerDTO;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.entities.Seller;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.SellerRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	private SellerDTO mapToDTO(Seller seller) {
		SellerDTO dto = new SellerDTO();
		dto.setId(seller.getId());
		dto.setStoreName(seller.getStoreName());
		dto.setGstNumber(seller.getGstNumber());
		if (seller.getUser() != null)
			dto.setUserId(seller.getUser().getId());
		return dto;
	}

	private Seller mapToEntity(SellerDTO dto) {
		Seller seller = new Seller();
		seller.setId(dto.getId());
		seller.setStoreName(dto.getStoreName());
		seller.setGstNumber(dto.getGstNumber());
		if (dto.getUserId() != null) {
			User user = userRepository.findById(dto.getUserId()).orElse(null);
			seller.setUser(user);
		}
		return seller;
	}

	@Override
	public SellerDTO createSeller(SellerDTO dto) {
		return mapToDTO(sellerRepository.save(mapToEntity(dto)));
	}

	@Override
	public SellerDTO getSellerById(Long id) {
		return sellerRepository.findById(id).map(this::mapToDTO).orElse(null);
	}

	@Override
	public List<SellerDTO> getAllSellers() {
		return sellerRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public SellerDTO updateSeller(Long id, SellerDTO dto) {
		dto.setId(id);
		return mapToDTO(sellerRepository.save(mapToEntity(dto)));
	}
	/*
	 * @Override public void deleteSeller(Long id) {
	 * sellerRepository.deleteById(id); }
	 */
	@Transactional
	@Override
	public void deleteSeller(Long id) {
	    Seller seller = sellerRepository.findById(id).orElse(null);
	    if (seller == null) throw new RuntimeException("Seller not found");

	    List<Product> products = seller.getProducts();
	    if (products != null && !products.isEmpty()) {
	        for (Product product : products) {
	            // ⚠️ Clear order items first to avoid FK constraint issue
	            if (product.getOrderItems() != null) {
	                product.getOrderItems().clear();
	            }

	            // Clear cart entries if any (optional)
	            if (product.getCarts() != null) {
	                product.getCarts().clear();
	            }

	            product.setSeller(null); // break relation
	            productRepository.delete(product); // delete product
	        }
	    }

	    seller.setProducts(null);
	    sellerRepository.delete(seller);
	}
}
