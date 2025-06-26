
  package com.hexaware.QuitQ.service;
  
  import java.util.List;
  
  import com.hexaware.QuitQ.dto.SellerDTO;
  
  public interface SellerService { SellerDTO createSeller(SellerDTO sellerDTO);
  SellerDTO getSellerById(Long id); List<SellerDTO> getAllSellers(); SellerDTO
  updateSeller(Long id, SellerDTO sellerDTO); void deleteSeller(Long id); }
 