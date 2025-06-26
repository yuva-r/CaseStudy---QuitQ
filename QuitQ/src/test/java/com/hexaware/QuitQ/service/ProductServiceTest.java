package com.hexaware.QuitQ.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.QuitQ.dto.ProductDTO;
import com.hexaware.QuitQ.entities.Product;
import com.hexaware.QuitQ.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
	// creating mock version of product repo
    @Mock
    private ProductRepository productRepository;
  //automatically injects mock version of product repo
    @InjectMocks
    private ProductService productService;
   //initialize mock and inject mock before it runs
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {
    	//creating fake obj to chk what repo will return
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        //finding by id 1
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        //calls actual method to test
        ProductDTO result = productService.getProductById(1L);
        //checking foe notnull
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        //verifying findbyid called once by mockrepo
        verify(productRepository, times(1)).findById(1L);
    }
}