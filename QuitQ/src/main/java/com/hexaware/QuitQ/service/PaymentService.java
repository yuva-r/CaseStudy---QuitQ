
  package com.hexaware.QuitQ.service;
  
  import java.util.List;
  
  import com.hexaware.QuitQ.dto.PaymentDTO;
  
  public interface PaymentService { PaymentDTO createPayment(PaymentDTO
  paymentDTO); PaymentDTO getPaymentById(Long id); List<PaymentDTO>
  getAllPayments(); PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);
  void deletePayment(Long id); }
 