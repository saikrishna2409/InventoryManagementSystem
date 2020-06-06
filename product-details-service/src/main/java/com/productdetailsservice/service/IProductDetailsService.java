package com.productdetailsservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.productdetailsservice.model.ProductDetails;

public interface IProductDetailsService {
	 
	public List<ProductDetails> searchProductService(String prodName);
	public ProductDetails addProduct(ProductDetails product) throws Exception;
	public ResponseEntity<HttpStatus> deleteProduct(Integer prodId) throws Exception;
}
