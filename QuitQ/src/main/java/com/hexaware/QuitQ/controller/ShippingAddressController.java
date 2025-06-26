package com.hexaware.QuitQ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.QuitQ.entities.ShippingAddress;
import com.hexaware.QuitQ.repository.ShippingAddressRepository;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/shipping")
public class ShippingAddressController {

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@PostMapping
	public ShippingAddress create(@RequestBody ShippingAddress address) {
		return shippingAddressRepository.save(address);
	}
	@GetMapping("/user/{userId}")
	public List<ShippingAddress> getByUserId(@PathVariable Long userId) {
	    return shippingAddressRepository.findByUserId(userId);
	}
	@GetMapping
	public List<ShippingAddress> getAll() {
		return shippingAddressRepository.findAll();
	}

	@GetMapping("/{id}")
	public ShippingAddress get(@PathVariable Long id) {
		return shippingAddressRepository.findById(id).orElse(null);
	}

	@PutMapping("/{id}")
	public ShippingAddress update(@PathVariable Long id, @RequestBody ShippingAddress addr) {
		ShippingAddress a = shippingAddressRepository.findById(id).orElse(null);
		if (a != null) {
			a.setUser(addr.getUser());
			a.setAddressLine1(addr.getAddressLine1());
			a.setAddressLine2(addr.getAddressLine2());
			a.setCity(addr.getCity());
			a.setState(addr.getState());
			a.setZipCode(addr.getZipCode());
			a.setCountry(addr.getCountry());
			return shippingAddressRepository.save(a);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		shippingAddressRepository.deleteById(id);
	}
}