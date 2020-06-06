package com.product.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.product.ProductsearchApplication;
import com.product.exception.DataNotFoundException;
import com.product.model.Product;
import com.product.repository.Productrepository;

@SpringBootTest(classes = ProductsearchApplication.class)
class ProductserviceimplTest {
	
	@InjectMocks
	Productserviceimpl productService;
	
	@Mock
	Productrepository productRep;
	
	@Test
	public void testGetProduct() throws Exception {	
		List<Product>  product = new ArrayList<>();
		product.add( new Product(3,"phone","Oneplus is awesome"));
		Mockito
			.when(productRep.findByName("phone"))
			.thenReturn(product);
		List<Product> retrievedProducts = productService.getProduct("phone");
		assertEquals(1, retrievedProducts.size());
	}
	
	@Test
	public void testForProductNotFound() throws Exception {
		List<Product> product = new ArrayList<Product>();
		Exception exception = assertThrows(DataNotFoundException.class, ()->{
			when(productRep.findByName("AnyProduct"))
			.thenReturn(product);
			productService.getProduct("AnyProduct");
		});
		
		String expectedMessage = "No Product Found With Given Name";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	public void testSaveProduct() throws Exception{
		Product product = new Product(1, "abc", "abc");
		when(productRep.save(product))
			.thenReturn(product);
		Product actualProduct = productService.addProduct(product);
		assertEquals(product, actualProduct);
	}
	
	@Test
	public void testSavingProductFailed() throws Exception{
		Product product = new Product(1, "abc", "abc");
		when(productRep.save(product))
			.thenReturn(null);
		Product actualProduct = productService.addProduct(product);
		assertEquals(null, actualProduct);
	}
	
	@Test
	public void testDeleteProduct() throws Exception{
		Product product = new Product(1, "abc", "abc");
		productRep.deleteById(product.getId());
		verify(productRep,times(1)).deleteById(product.getId());
	}
}
