package com.product.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.ProductCache.ProductCache;
//import com.inventoryservice.controller.Inventorycontroller;
import com.product.exception.DataNotFoundException;
import com.product.model.Product;
import com.product.service.Productservice;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins="*")
@RestController
@Slf4j
@RequestMapping("/product")
public class Productcontroller {
	
	@Autowired
	private Productservice productservice;
	@Autowired
	ProductCache productCache;

	@GetMapping("/productsearch/{name}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable(value="name") String prodName) throws DataNotFoundException{
		List<Product> productList = new ArrayList<Product>();
		log.info("inside serch product information");
		try{
			log.debug("serarching for prduct information with name : "+ prodName);
			productList = productservice.getProduct(prodName);
		}catch(DataNotFoundException exception) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}
	
	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody Product product) throws Exception{
		Product savedProduct = new Product();
		try {
			savedProduct = productservice.addProduct(product);
		}catch(Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer prodId) throws Exception{
		try {
			productservice.deleteById(prodId);
		}catch(Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Product> updateProject(@RequestBody Product product) throws Exception, DataNotFoundException{
		Product updatedProduct = new Product();
		try {
			productservice.getProduct(product.getId());
			updatedProduct = productservice.updateProduct(product);
		}catch(DataNotFoundException ex) {
			throw new DataNotFoundException("No Product Found with given id");
		}
		catch(Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
	}
}
