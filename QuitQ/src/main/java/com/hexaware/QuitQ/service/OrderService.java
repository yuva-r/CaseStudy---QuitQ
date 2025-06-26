
package com.hexaware.QuitQ.service;

import java.util.List;

import com.hexaware.QuitQ.dto.OrderDTO;

public interface OrderService {
	OrderDTO createOrder(OrderDTO orderDTO);

	OrderDTO getOrderById(Long id);

	List<OrderDTO> getAllOrders();

	OrderDTO updateOrder(Long id, OrderDTO orderDTO);

	void deleteOrder(Long id);

	List<OrderDTO> getOrdersByUserId(Long userId);
}
