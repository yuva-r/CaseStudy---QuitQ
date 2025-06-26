
package com.hexaware.QuitQ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.hexaware.QuitQ.dto.PaymentDTO;
import com.hexaware.QuitQ.entities.Order;
import com.hexaware.QuitQ.entities.Payment;
import com.hexaware.QuitQ.repository.OrderRepository;
import com.hexaware.QuitQ.repository.PaymentRepository;
import com.hexaware.QuitQ.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:5173")

@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentRepository paymentRepository;
	 @Autowired
	    private OrderRepository orderRepository;
	    @Autowired
	    private UserRepository userRepository;
		/*
		 * @PostMapping public Payment createPayment(@RequestBody Payment payment) {
		 * return paymentRepository.save(payment); }
		 */

	
	@GetMapping
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	

	@GetMapping("/{id}")
	public Payment getPayment(@PathVariable Long id) {
		return paymentRepository.findById(id).orElse(null);
	}

	
	@PutMapping("/{id}")
	public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
		Payment p = paymentRepository.findById(id).orElse(null);
		if (p != null) {
			p.setAmount(payment.getAmount());
			p.setPaymentDate(payment.getPaymentDate());
			p.setPaymentMethod(payment.getPaymentMethod());
			p.setOrder(payment.getOrder());
			return paymentRepository.save(p);
		}
		return null;
	}

	
	@DeleteMapping("/{id}")
	public void deletePayment(@PathVariable Long id) {
		paymentRepository.deleteById(id);
	}
	@PostMapping
	public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
	    Payment payment = new Payment();
	    payment.setAmount(paymentDTO.getAmount());
	    payment.setPaymentMethod(paymentDTO.getPaymentMethod());
	    payment.setPaymentStatus(paymentDTO.getPaymentStatus());
	    payment.setPaymentDate(paymentDTO.getPaymentDate());
	    payment.setUser(userRepository.findById(paymentDTO.getUserId()).orElse(null));
	    if (paymentDTO.getOrderId() != null) {
	        Order order = orderRepository.findById(paymentDTO.getOrderId()).orElse(null);
	        if (order != null) {
	            payment.setOrder(order);
	        }
	    }
	    Payment savedPayment = paymentRepository.save(payment);
	    return ResponseEntity.ok(savedPayment);
	}

}
