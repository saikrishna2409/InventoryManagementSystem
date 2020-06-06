package com.inventoryservice.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventoryservice.exception.DataNotFoundException;
import com.inventoryservice.model.InventoryEntity;
import com.inventoryservice.repository.Inventoryrepository;
import com.inventoryservice.service.Inventoryservice;

@Service
public class Inventoryserviceimpl implements Inventoryservice {

	@Autowired
	private Inventoryrepository inventoryrepository;
	
	public InventoryEntity getProduct( Integer productid) throws DataNotFoundException{
		
		Optional<InventoryEntity> productInventory =  inventoryrepository.findById(productid);
		InventoryEntity inventory = new InventoryEntity();
		if(productInventory.isPresent()) {
			inventory = productInventory.get();
		}else {
			throw new DataNotFoundException();
		}
		return inventory;
	}

	@Override
	public InventoryEntity addProductToInventory(InventoryEntity inventory) {
		return inventoryrepository.save(inventory);
	}

	@Override
	public void deleteById(Integer prodId) {
		inventoryrepository.deleteById(prodId);
	}

	@Override
	public InventoryEntity updateProduct(InventoryEntity updatedInventory) {
		return inventoryrepository.save(updatedInventory);
	}
	
	public static String checkStatic(String check) {
		return check;
	}
	
	private String checkPrivate(String check) {
		return check;
	}
}


