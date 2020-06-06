package com.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventoryservice.exception.DataNotFoundException;
import com.inventoryservice.model.InventoryEntity;
import com.inventoryservice.service.Inventoryservice;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/inventory")
public class Inventorycontroller {
	
	@Autowired
	private Inventoryservice inventoryservice;
		
	@GetMapping("/product/{id}")
	public ResponseEntity<InventoryEntity> getProductbyId(@PathVariable("id") Integer productId) throws DataNotFoundException{
		InventoryEntity productInventory = new InventoryEntity();
		log.info("inside search product inventory");
		try{
			log.debug("searching inventory of product with id : "+ productId);
			productInventory = inventoryservice.getProduct(productId);
		}catch(DataNotFoundException exception) {
			log.debug("caught exception");
			throw new DataNotFoundException();
		}
		return new ResponseEntity<InventoryEntity>(productInventory, HttpStatus.OK);
	}
	
	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody InventoryEntity inventory) throws Exception{
		InventoryEntity savedInventory = new InventoryEntity();
		try {
			savedInventory = inventoryservice.addProductToInventory(inventory);
		}catch(Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<InventoryEntity>(savedInventory, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer prodId) throws Exception{
		try{
			inventoryservice.deleteById(prodId);
		}catch(Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateProject(@RequestBody InventoryEntity updatedInventory) throws DataNotFoundException, Exception{
		InventoryEntity inventory = new InventoryEntity();
		try {
			inventoryservice.getProduct(updatedInventory.getId());
			inventory = inventoryservice.updateProduct(updatedInventory);
		}catch(DataNotFoundException ex) {
			throw new DataNotFoundException();
		}
		catch(Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<InventoryEntity>(inventory, HttpStatus.OK);
	}

}
