package com.hexaware.QuitQ.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.QuitQ.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	void deleteAllByOrderId(Long id);

}
