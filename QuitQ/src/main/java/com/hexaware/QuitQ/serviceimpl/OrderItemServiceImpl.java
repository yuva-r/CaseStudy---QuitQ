
package com.hexaware.QuitQ.serviceimpl;

import com.hexaware.QuitQ.dto.OrderItemDTO;
import com.hexaware.QuitQ.entities.OrderItem;
import com.hexaware.QuitQ.repository.OrderItemRepository;
import com.hexaware.QuitQ.repository.OrderRepository;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.service.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public OrderItemDTO createOrderItem(OrderItemDTO dto) {
		OrderItem item = new OrderItem();
		item.setQuantity(dto.getQuantity());
		item.setPrice(dto.getPrice());

		if (dto.getOrderId() != null) {
			orderRepository.findById(dto.getOrderId()).ifPresent(item::setOrder);
		}

		if (dto.getProductId() != null) {
			productRepository.findById(dto.getProductId()).ifPresent(item::setProduct);
		}

		return mapToDTO(orderItemRepository.save(item));
	}

	@Override
	public OrderItemDTO getOrderItemById(Long id) {
		return orderItemRepository.findById(id).map(this::mapToDTO).orElse(null);
	}

	@Override
	public List<OrderItemDTO> getAllOrderItems() {
		return orderItemRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public OrderItemDTO updateOrderItem(Long id, OrderItemDTO dto) {
		Optional<OrderItem> optional = orderItemRepository.findById(id);
		if (optional.isPresent()) {
			OrderItem item = optional.get();
			item.setQuantity(dto.getQuantity());
			item.setPrice(dto.getPrice());

			if (dto.getOrderId() != null) {
				orderRepository.findById(dto.getOrderId()).ifPresent(item::setOrder);
			}

			if (dto.getProductId() != null) {
				productRepository.findById(dto.getProductId()).ifPresent(item::setProduct);
			}

			return mapToDTO(orderItemRepository.save(item));
		}
		return null;
	}

	@Override
	public void deleteOrderItem(Long id) {
		orderItemRepository.deleteById(id);
	}

	private OrderItemDTO mapToDTO(OrderItem item) {
		OrderItemDTO dto = new OrderItemDTO();
		dto.setId(item.getId());
		dto.setQuantity(item.getQuantity());
		dto.setPrice(item.getPrice());
		if (item.getOrder() != null)
			dto.setOrderId(item.getOrder().getId());
		if (item.getProduct() != null)
			dto.setProductId(item.getProduct().getId());
		return dto;
	}
}
