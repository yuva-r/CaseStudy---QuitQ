package com.hexaware.QuitQ.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hexaware.QuitQ.entities.OrderItem;
import com.hexaware.QuitQ.repository.OrderItemRepository;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping
    public OrderItem create(@RequestBody OrderItem item) {
        return orderItemRepository.save(item);
    }

    @GetMapping
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @PutMapping("/{id}")
    public OrderItem update(@PathVariable Long id, @RequestBody OrderItem oi) {
        OrderItem item = orderItemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setOrder(oi.getOrder());
            item.setProduct(oi.getProduct());
            item.setQuantity(oi.getQuantity());
            item.setPrice(oi.getPrice());
            return orderItemRepository.save(item);
        }
        return null;
    }
    @GetMapping("/{id}")
    public OrderItem get(@PathVariable Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemRepository.deleteById(id);
    }
}
