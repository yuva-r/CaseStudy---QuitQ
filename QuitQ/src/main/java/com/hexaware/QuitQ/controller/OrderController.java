package com.hexaware.QuitQ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.QuitQ.dto.OrderDTO;
import com.hexaware.QuitQ.entities.Order;
import com.hexaware.QuitQ.repository.OrderRepository;
import com.hexaware.QuitQ.repository.ProductRepository;
import com.hexaware.QuitQ.repository.ShippingAddressRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;

	/*
	 * @PostMapping public Order placeOrder(@RequestBody Order order) {
	 * order.setOrderDate(new Date()); return orderRepository.save(order); }
	 */

	/*
	 * @PostMapping("/place") public OrderDTO placeOrder(@RequestBody OrderDTO dto)
	 * { return orderService.createOrder(dto); }
	 */
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order o) {
        Order existing = orderRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus(o.getStatus());
            existing.setTotalAmount(o.getTotalAmount());
            existing.setShippingAddress(o.getShippingAddress());
            return orderRepository.save(existing);
        }
        return null;
    }
    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }
	/*
	 * @GetMapping("/user/{userId}") public List<Order>
	 * getOrdersByUser(@PathVariable Long userId) { return
	 * orderRepository.findByUserId(userId); // Add this method to OrderRepository
	 * if needed }
	 */

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
	
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }
}