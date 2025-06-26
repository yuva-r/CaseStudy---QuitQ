
package com.hexaware.QuitQ.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQ.dto.ShippingAddressDTO;
import com.hexaware.QuitQ.entities.ShippingAddress;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.repository.ShippingAddressRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.ShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private UserRepository userRepository;

	private ShippingAddressDTO mapToDTO(ShippingAddress address) {
		ShippingAddressDTO dto = new ShippingAddressDTO();
		dto.setId(address.getId());
		dto.setAddressLine1(address.getAddressLine1());
		dto.setAddressLine2(address.getAddressLine2());
		dto.setCity(address.getCity());
		dto.setState(address.getState());
		dto.setZipCode(address.getZipCode());
		dto.setCountry(address.getCountry());
		if (address.getUser() != null)
			dto.setUserId(address.getUser().getId());
		return dto;
	}

	private ShippingAddress mapToEntity(ShippingAddressDTO dto) {
		ShippingAddress address = new ShippingAddress();
		address.setId(dto.getId());
		address.setAddressLine1(dto.getAddressLine1());
		address.setAddressLine2(dto.getAddressLine2());
		address.setCity(dto.getCity());
		address.setState(dto.getState());
		address.setZipCode(dto.getZipCode());
		address.setCountry(dto.getCountry());
		if (dto.getUserId() != null) {
			User user = userRepository.findById(dto.getUserId()).orElse(null);
			address.setUser(user);
		}
		return address;
	}

	@Override
	public ShippingAddressDTO createShippingAddress(ShippingAddressDTO dto) {
		return mapToDTO(shippingAddressRepository.save(mapToEntity(dto)));
	}

	@Override
	public ShippingAddressDTO getShippingAddressById(Long id) {
		return shippingAddressRepository.findById(id).map(this::mapToDTO).orElse(null);
	}

	@Override
	public List<ShippingAddressDTO> getAllShippingAddresses() {
		return shippingAddressRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public ShippingAddressDTO updateShippingAddress(Long id, ShippingAddressDTO dto) {
		dto.setId(id);
		return mapToDTO(shippingAddressRepository.save(mapToEntity(dto)));
	}

	@Override
	public void deleteShippingAddress(Long id) {
		shippingAddressRepository.deleteById(id);
	}

}
