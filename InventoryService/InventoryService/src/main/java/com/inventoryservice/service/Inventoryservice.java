package com.inventoryservice.service;

import com.inventoryservice.model.InventoryEntity;

public interface Inventoryservice {

	public InventoryEntity getProduct( Integer productid);
	public InventoryEntity addProductToInventory(InventoryEntity inventory);
	public void deleteById(Integer prodId);
	public InventoryEntity updateProduct(InventoryEntity updatedInventory);
}
