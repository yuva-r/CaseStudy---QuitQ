
package com.hexaware.QuitQ.serviceimpl;

import com.hexaware.QuitQ.dto.PaymentDTO;
import com.hexaware.QuitQ.entities.Order;
import com.hexaware.QuitQ.entities.Payment;
import com.hexaware.QuitQ.repository.OrderRepository;
import com.hexaware.QuitQ.repository.PaymentRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	/*
	 * @Override public PaymentDTO createPayment(PaymentDTO dto) { Payment payment =
	 * new Payment(); payment.setAmount(dto.getAmount());
	 * payment.setPaymentDate(dto.getPaymentDate());
	 * payment.setPaymentMethod(dto.getPaymentMethod());
	 * payment.setPaymentStatus(dto.getPaymentStatus());
	 * 
	 * if (dto.getOrderId() != null) {
	 * orderRepository.findById(dto.getOrderId()).ifPresent(payment::setOrder); }
	 * 
	 * return mapToDTO(paymentRepository.save(payment)); }
	 */
	@Override
	@Transactional
	public PaymentDTO createPayment(PaymentDTO dto) {
	    Payment payment = new Payment();
	    payment.setAmount(dto.getAmount());
	    payment.setPaymentDate(dto.getPaymentDate());
	    payment.setPaymentMethod(dto.getPaymentMethod());
	    payment.setPaymentStatus(dto.getPaymentStatus());

	    // If no order ID, create a new order
	    if (dto.getOrderId() == null) {
	        Order order = new Order();
	        order.setTotalAmount(dto.getAmount());
	        order.setStatus("Confirmed");
	        order.setOrderDate(new Date());

	        // Optional: link to a user if provided
	        if (dto.getUserId() != null) {
	            userRepository.findById(dto.getUserId()).ifPresent(order::setUser);
	        }

	        Order savedOrder = orderRepository.save(order);
	        payment.setOrder(savedOrder);
	    } else {
	        // Link to existing order
	        orderRepository.findById(dto.getOrderId()).ifPresent(payment::setOrder);
	    }

	    return mapToDTO(paymentRepository.save(payment));
	}

	@Override
	public PaymentDTO getPaymentById(Long id) {
		return paymentRepository.findById(id).map(this::mapToDTO).orElse(null);
	}

	@Override
	public List<PaymentDTO> getAllPayments() {
		return paymentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public PaymentDTO updatePayment(Long id, PaymentDTO dto) {
		Optional<Payment> optional = paymentRepository.findById(id);
		if (optional.isPresent()) {
			Payment payment = optional.get();
			payment.setAmount(dto.getAmount());
			payment.setPaymentDate(dto.getPaymentDate());
			payment.setPaymentMethod(dto.getPaymentMethod());
			payment.setPaymentStatus(dto.getPaymentStatus());

			if (dto.getOrderId() != null) {
				orderRepository.findById(dto.getOrderId()).ifPresent(payment::setOrder);
			}

			return mapToDTO(paymentRepository.save(payment));
		}
		return null;
	}

	@Override
	public void deletePayment(Long id) {
		paymentRepository.deleteById(id);
	}

	private PaymentDTO mapToDTO(Payment payment) {
		PaymentDTO dto = new PaymentDTO();
		dto.setId(payment.getId());
		dto.setAmount(payment.getAmount());
		dto.setPaymentDate(payment.getPaymentDate());
		dto.setPaymentMethod(payment.getPaymentMethod());
		dto.setPaymentStatus(payment.getPaymentStatus());
		if (payment.getOrder() != null)
			dto.setOrderId(payment.getOrder().getId());
		return dto;
	}

}
