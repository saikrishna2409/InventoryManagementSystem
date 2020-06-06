package com.productdetailsservice.service.impl;

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
import org.springframework.web.client.RestTemplate;
import com.productdetailsservice.ProductDetailsServiceApplication;
import com.productdetailsservice.exception.DataNotFoundException;
import com.productdetailsservice.model.Inventory;
import com.productdetailsservice.model.Product;
import com.productdetailsservice.model.ProductDetails;

@SpringBootTest(classes = ProductDetailsServiceApplication.class)
class ProductDetailsServiceServiceTest {
	
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	ProductDetailsServiceService productDetailsService;
	@Test
	public void productDetailsFound() throws Exception{
		
		List<Product> product = Arrays.asList(new Product(1, "tshirt", "v neck tshirts"));
		Product[] arrayProduct = (Product[]) product.toArray();
		Inventory inventory = new Inventory(1, 5);
		List<ProductDetails> actualProductDetails = Arrays.asList(new ProductDetails("v neck shirts", "tshirt", 5));
		
		when(restTemplate.getForEntity("http://product-service/product/productsearch/" + "tshirt", Product[].class))
		.thenReturn(new ResponseEntity<Product[]>(arrayProduct, HttpStatus.OK));
		when(restTemplate.getForEntity("http://inventory-service/inventory/product/" + 1, Inventory.class))
		.thenReturn(new ResponseEntity<Inventory>(inventory, HttpStatus.OK));
		
		List<ProductDetails> expectedProductDetails = productDetailsService.searchProductService("tshirt");
		assertEquals(expectedProductDetails.get(0).getName(), actualProductDetails.get(0).getName());
	}
	
	@Test
	public void productDetailsNotFound() throws Exception{
				
		Exception exception = assertThrows(DataNotFoundException.class, ()->{
			when(restTemplate.getForEntity("http://product-service/product/productsearch/" + "tshirt", Product[].class))
			.thenThrow(new DataNotFoundException());
			when(restTemplate.getForEntity("http://inventory-service/inventory/product/" + 1, Inventory.class))
			.thenThrow(new DataNotFoundException());
			productDetailsService.searchProductService("tshirt");
		});
		
		String expectedMessage = "No Product With This Name Found";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
}
