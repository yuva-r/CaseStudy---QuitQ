
  package com.hexaware.QuitQ.service;
  
  import java.util.List;
  
  import com.hexaware.QuitQ.dto.ShippingAddressDTO;
  
  public interface ShippingAddressService { ShippingAddressDTO
  createShippingAddress(ShippingAddressDTO addressDTO); ShippingAddressDTO
  getShippingAddressById(Long id); List<ShippingAddressDTO>
  getAllShippingAddresses(); ShippingAddressDTO updateShippingAddress(Long id,
  ShippingAddressDTO addressDTO); void deleteShippingAddress(Long id); }
 