package com.inventoryservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.inventoryservice.exception.DataNotFoundException;
import com.inventoryservice.model.InventoryEntity;
import com.inventoryservice.service.Inventoryservice;

@SpringBootTest
class InventorycontrollerTest {
	
	@Mock
	Inventoryservice inventoryservice;
	
	@InjectMocks
	Inventorycontroller inventoryController;
	
	@Test
	public void inventoryFound() throws Exception {
		
		InventoryEntity inventory = new InventoryEntity(1, 5);
		
		when(inventoryservice.getProduct(1)).thenReturn(inventory);
		
		ResponseEntity<InventoryEntity>actual = inventoryController.getProductbyId(1);
		
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		assertEquals(inventory.getProductQty(), actual.getBody().getProductQty());
	}
	
	@Test
	public void inventoryNotFound() throws Exception {
		
		Exception exception = assertThrows(DataNotFoundException.class, () ->{
			when(inventoryservice.getProduct(1)).thenThrow(DataNotFoundException.class);
			inventoryController.getProductbyId(1);
		});
		
		assertEquals(exception.getMessage(), "No Product With This Name Found in Inventory");
	}
}
