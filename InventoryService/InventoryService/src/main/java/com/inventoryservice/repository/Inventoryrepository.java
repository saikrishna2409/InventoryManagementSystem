package com.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventoryservice.model.InventoryEntity;

@Repository
public interface Inventoryrepository extends JpaRepository<InventoryEntity, Integer> {

}
