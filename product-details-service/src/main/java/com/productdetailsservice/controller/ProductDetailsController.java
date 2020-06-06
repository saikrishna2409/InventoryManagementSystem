package com.productdetailsservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.inventoryservice.controller.Inventorycontroller;

import com.productdetailsservice.exception.DataNotFoundException;
import com.productdetailsservice.model.ProductDetails;
import com.productdetailsservice.service.impl.ProductDetailsServiceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/productDetailsService")
public class ProductDetailsController {
	
	@Autowired
	ProductDetailsServiceService productDetailsService;
	
	public ProductDetailsController() {
		
	}
	
	@GetMapping("/product/{name}")
	public ResponseEntity<?> searchProduct(@PathVariable("name") String prodName){
		List<ProductDetails> productsDetails = null;
		log.info("inside search controller");
		try {	
			log.debug("searching Product with name : "+prodName);
			productsDetails = productDetailsService.searchProductService(prodName);
		}catch(DataNotFoundException exception) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<List<ProductDetails>>(productsDetails, HttpStatus.OK);
	}
	@GetMapping("/login")
	public ResponseEntity<?> login(){
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody ProductDetails product) throws Exception{
		ProductDetails addedProduct = productDetailsService.addProduct(product);
		return new ResponseEntity<ProductDetails>(addedProduct, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer prodId) throws Exception{
		return productDetailsService.deleteProduct(prodId);
	}
}
