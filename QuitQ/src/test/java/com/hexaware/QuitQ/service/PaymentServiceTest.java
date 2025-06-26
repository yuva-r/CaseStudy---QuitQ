package com.hexaware.QuitQ.service;

import com.hexaware.QuitQ.dto.PaymentDTO;
import com.hexaware.QuitQ.entities.Order;
import com.hexaware.QuitQ.entities.Payment;
import com.hexaware.QuitQ.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPaymentById() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100.0);
        payment.setPaymentMethod("Credit Card");
        payment.setPaymentStatus("Success");
        payment.setPaymentDate(LocalDateTime.now());
        Order order = new Order();
        order.setId(101L);
        payment.setOrder(order);

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        PaymentDTO result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Credit Card", result.getPaymentMethod());
        assertEquals(101L, result.getOrderId());

        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreatePayment() {
        PaymentDTO dto = new PaymentDTO();
        dto.setAmount(200.0);
        dto.setPaymentMethod("UPI");
        dto.setPaymentStatus("Pending");
        dto.setPaymentDate(LocalDateTime.now());
        dto.setOrderId(102L);

        Payment savedEntity = new Payment();
        savedEntity.setId(2L);
        savedEntity.setAmount(dto.getAmount());
        savedEntity.setPaymentMethod(dto.getPaymentMethod());
        savedEntity.setPaymentStatus(dto.getPaymentStatus());
        savedEntity.setPaymentDate(dto.getPaymentDate());
        //associating payment with order hence doing this
        Order order = new Order();
        order.setId(dto.getOrderId());
        savedEntity.setOrder(order);

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedEntity);

        PaymentDTO result = paymentService.createPayment(dto);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("UPI", result.getPaymentMethod());
        assertEquals(102L, result.getOrderId());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testDeletePayment() {
        Long id = 5L;
        //checks wheather it correctly calls the repo
        doNothing().when(paymentRepository).deleteById(id);

        paymentService.deletePayment(id);

        verify(paymentRepository, times(1)).deleteById(id);
    }
}