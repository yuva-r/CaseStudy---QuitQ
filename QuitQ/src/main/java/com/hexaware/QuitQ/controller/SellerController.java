package com.hexaware.QuitQ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.QuitQ.entities.Seller;
import com.hexaware.QuitQ.repository.SellerRepository;
import com.hexaware.QuitQ.service.SellerService;

@RestController
@RequestMapping("/api/sellers")
@CrossOrigin("http://localhost:5173")
public class SellerController {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SellerService sellerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Seller addSeller(@RequestBody Seller seller) {
        return sellerRepository.save(seller);
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Seller getSellerById(@PathVariable Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Seller updateSeller(@PathVariable Long id, @RequestBody Seller s) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if (seller != null) {
            seller.setUser(s.getUser());
            seller.setStoreName(s.getStoreName());
            seller.setGstNumber(s.getGstNumber());
            seller.setProducts(s.getProducts());
            return sellerRepository.save(seller);
        }
        return null;
    }

	/*
	 * @DeleteMapping("/{id}") public void deleteSeller(@PathVariable Long id) {
	 * sellerService.deleteSeller(id); }
	 */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
    
    }
}