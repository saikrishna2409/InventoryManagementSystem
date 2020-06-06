package com.product.ProductCache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.product.model.Product;
import com.product.repository.Productrepository;

@Component
public class ProductCache {
	@Autowired
	Productrepository productRepository;
	
	
	@Cacheable(value = "productCache", key ="#name")
	public List<Product> getProductByName(String name) {
		System.out.println("Retreiving from database for name: " + name);
		return productRepository.findByName(name);
	}
}
