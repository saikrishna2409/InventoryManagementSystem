package com.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.product.ProductCache.ProductCache;
import com.product.exception.DataNotFoundException;
import com.product.model.Product;
import com.product.repository.Productrepository;
import com.product.service.Productservice;


@Service
public class Productserviceimpl implements Productservice {
	
	@Autowired
	private Productrepository productrepository;
//	@Autowired
//	private ProductCache productCache;

	@Override
	//@Cacheable(value = "productCache", key ="#prodName")
	public List<Product> getProduct(String prodName) throws DataNotFoundException{
		List<Product> product =productrepository.findByName(prodName);

		if(product.size() > 0) {
			return product ;
		}else {
			throw new DataNotFoundException();
		}
	}

	@Override
	public Product addProduct(Product product) {
		return productrepository.save(product);
	}

	@Override
	public void deleteById(Integer prodId) {
		productrepository.deleteById(prodId);
	}

	@Override
	public Product updateProduct(Product product) {
		return productrepository.save(product);
	}

	@Override
	public void getProduct(Integer id) throws DataNotFoundException{
		Product productAvailable = productrepository.getOne(id);
		if(productAvailable == null) {
			throw new DataNotFoundException();
		}
	}
}
