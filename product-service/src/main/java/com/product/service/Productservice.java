package com.product.service;

import com.product.exception.DataNotFoundException;
import com.product.model.Product;
import java.util.List;


public interface Productservice {
	
	public List<Product> getProduct(String itemname)throws DataNotFoundException;
	public Product addProduct(Product product);
	public void deleteById(Integer prodId);
	public Product updateProduct(Product product);
	public void getProduct(Integer id);
}
