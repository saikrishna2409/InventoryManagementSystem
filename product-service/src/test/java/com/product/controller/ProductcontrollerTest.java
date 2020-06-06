package com.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.product.ProductsearchApplication;
import com.product.exception.DataNotFoundException;
import com.product.model.Product;
import com.product.service.Productservice;


@SpringBootTest(classes = ProductsearchApplication.class)
class ProductcontrollerTest {
	@Mock
	private Productservice productservice;
	
	@InjectMocks
	Productcontroller productController;
	
	@Test
	public void productByNameFound() throws Exception{
		List<Product> productList = Arrays.asList(new Product(1, "abc", "abc"));
		when(productservice.getProduct("abc")).thenReturn(productList);
		
		ResponseEntity<List<Product>> response = productController.getProductByName("abc");
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().get(0).getName(), productList.get(0).getName());
	}
	
	@Test
	public void podcutByNameNotFound() throws Exception{
		Exception exception = assertThrows(DataNotFoundException.class, ()->{
			when(productservice.getProduct("abc")).thenThrow(DataNotFoundException.class);
			productController.getProductByName("abc");
		});
		
		String expectedMessage = "No Product Found With Given Name";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
}
