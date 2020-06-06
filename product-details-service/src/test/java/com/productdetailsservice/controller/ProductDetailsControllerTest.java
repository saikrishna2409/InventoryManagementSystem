package com.productdetailsservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.productdetailsservice.ProductDetailsServiceApplication;
import com.productdetailsservice.exception.DataNotFoundException;
import com.productdetailsservice.model.ProductDetails;
import com.productdetailsservice.service.impl.ProductDetailsServiceService;

@SpringBootTest(classes = ProductDetailsServiceApplication.class)
class ProductDetailsControllerTest {

	@Mock
	ProductDetailsServiceService productDetailsService;
	
	@InjectMocks
	ProductDetailsController productDetailsController;
	
	@Test
	public void testProductFound() throws Exception{
		
		List<ProductDetails> productList = Arrays.asList(new ProductDetails("Oneplus is awesome", "phone", 10));
		
		when(productDetailsService.searchProductService("phone"))
			.thenReturn(productList);
		ResponseEntity expectedresponse = productDetailsController.searchProduct("phone");
		assertEquals(expectedresponse.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testProductNotFound() throws Exception{
		
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, ()->{
			
			when(productDetailsService.searchProductService("anystring"))
				.thenThrow(new DataNotFoundException());
			productDetailsController.searchProduct("anystring");
		});
		
		String expectedMessage = "No Product With This Name Found";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
}
