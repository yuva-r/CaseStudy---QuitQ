package com.hexaware.QuitQ.serviceimpl;

import com.hexaware.QuitQ.dto.*;
import com.hexaware.QuitQ.entities.*;
import com.hexaware.QuitQ.repository.*;
import com.hexaware.QuitQ.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	@Override
	public OrderDTO createOrder(OrderDTO dto) {
		Order order = new Order();
		order.setTotalAmount(dto.getTotalAmount());
		order.setOrderDate(dto.getOrderDate());
		order.setStatus(dto.getStatus());

		// Set user
		if (dto.getUserId() != null) {
			userRepository.findById(dto.getUserId()).ifPresent(order::setUser);
		}

		// Set shipping address (must be managed entity)
		if (dto.getShippingAddressId() != null) {
			ShippingAddress sa = shippingAddressRepository.findById(dto.getShippingAddressId())
					.orElseThrow(() -> new RuntimeException("Invalid shipping address ID"));
			order.setShippingAddress(sa);
		}

		// Save order first
		Order savedOrder = orderRepository.save(order);

		// Save OrderItems
		List<OrderItem> savedItems = new ArrayList<>();
		if (dto.getOrderItems() != null && !dto.getOrderItems().isEmpty()) {
			for (OrderItemDTO itemDTO : dto.getOrderItems()) {
				OrderItem item = new OrderItem();
				item.setQuantity(itemDTO.getQuantity());
				item.setPrice(itemDTO.getPrice());

				// Set product
				if (itemDTO.getProductId() != null) {
					Product product = productRepository.findById(itemDTO.getProductId())
							.orElseThrow(() -> new RuntimeException("Invalid product ID"));
					item.setProduct(product);
				}

				item.setOrder(savedOrder);
				savedItems.add(orderItemRepository.save(item));
			}
		}

		savedOrder.setItems(savedItems); // Optional if mapped
		return mapToDTO(savedOrder);
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		return orderRepository.findById(id).map(this::mapToDTO).orElse(null);
	}
	@Override
	public List<OrderDTO> getOrdersByUserId(Long userId) {
	    List<Order> orders = orderRepository.findByUserId(userId);
	    return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	@Transactional
	@Override
	public List<OrderDTO> getAllOrders() {
		return orderRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public OrderDTO updateOrder(Long id, OrderDTO dto) {
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isPresent()) {
			Order order = optional.get();
			order.setTotalAmount(dto.getTotalAmount());
			order.setOrderDate(dto.getOrderDate());
			order.setStatus(dto.getStatus());

			if (dto.getUserId() != null) {
				userRepository.findById(dto.getUserId()).ifPresent(order::setUser);
			}

			if (dto.getShippingAddressId() != null) {
				shippingAddressRepository.findById(dto.getShippingAddressId()).ifPresent(order::setShippingAddress);
			}

			return mapToDTO(orderRepository.save(order));
		}
		return null;
	}

	@Transactional
	@Override
	public void deleteOrder(Long id) {
		// Delete associated order items first
		orderItemRepository.deleteAllByOrderId(id);
		// Delete order itself
		orderRepository.deleteById(id);
	}

	private OrderDTO mapToDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setOrderDate(order.getOrderDate());
		dto.setStatus(order.getStatus());

		if (order.getUser() != null) {
			dto.setUserId(order.getUser().getId());
			dto.setUserName(order.getUser().getName());
			dto.setUserEmail(order.getUser().getEmail());
		}

		/*
		 * // Map items List<OrderItemDTO> itemDTOs = new ArrayList<>(); if
		 * (order.getItems() != null) { for (OrderItem item : order.getItems()) {
		 * OrderItemDTO itemDTO = new OrderItemDTO(); itemDTO.setId(item.getId());
		 * itemDTO.setQuantity(item.getQuantity()); itemDTO.setPrice(item.getPrice());
		 * itemDTO.setOrderId(order.getId()); if (item.getProduct() != null) {
		 * itemDTO.setProductId(item.getProduct().getId()); } itemDTOs.add(itemDTO); } }
		 * dto.setOrderItems(itemDTOs);
		 */

		// Map items
		List<OrderItemDTO> itemDTOs = new ArrayList<>();
		if (order.getItems() != null) {
			for (OrderItem item : order.getItems()) {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setQuantity(item.getQuantity());
				itemDTO.setPrice(item.getPrice());
				itemDTO.setOrderId(order.getId());

				if (item.getProduct() != null) {
					itemDTO.setProductId(item.getProduct().getId());
					itemDTO.setProductName(item.getProduct().getName());
					itemDTO.setProductImageUrl(item.getProduct().getImageUrl());
				}

				itemDTOs.add(itemDTO);
			}
			dto.setOrderItems(itemDTOs);
		}

		// Map shipping address
		if (order.getShippingAddress() != null) {
			ShippingAddressDTO addressDTO = new ShippingAddressDTO();
			addressDTO.setId(order.getShippingAddress().getId());
			addressDTO.setAddressLine1(order.getShippingAddress().getAddressLine1());
			addressDTO.setAddressLine2(order.getShippingAddress().getAddressLine2());
			addressDTO.setCity(order.getShippingAddress().getCity());
			addressDTO.setState(order.getShippingAddress().getState());
			addressDTO.setZipCode(order.getShippingAddress().getZipCode());
			addressDTO.setCountry(order.getShippingAddress().getCountry());
			if (order.getShippingAddress().getUser() != null) {
				addressDTO.setUserId(order.getShippingAddress().getUser().getId());
			}
			dto.setShippingAddress(addressDTO);
		}

		// Map payment
		Payment payment = paymentRepository.findByOrderId(order.getId());
		if (payment != null) {
			PaymentDTO paymentDTO = new PaymentDTO();
			paymentDTO.setId(payment.getId());
			paymentDTO.setAmount(payment.getAmount());
			paymentDTO.setPaymentMethod(payment.getPaymentMethod());
			paymentDTO.setPaymentStatus(payment.getPaymentStatus());
			paymentDTO.setPaymentDate(payment.getPaymentDate());
			paymentDTO.setUserId(payment.getUser() != null ? payment.getUser().getId() : null);
			dto.setPayment(paymentDTO);
		}

		return dto;
	}
}