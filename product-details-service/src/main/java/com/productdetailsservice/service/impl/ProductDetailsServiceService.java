package com.productdetailsservice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.productdetailsservice.constants.Constants;
import com.productdetailsservice.exception.DataNotFoundException;
import com.productdetailsservice.model.Inventory;
import com.productdetailsservice.model.Product;
import com.productdetailsservice.model.ProductDetails;
import com.productdetailsservice.service.IProductDetailsService;

import lombok.extern.slf4j.Slf4j;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@Slf4j
public class ProductDetailsServiceService implements IProductDetailsService{

	@Autowired
	RestTemplate restTemplate;
	public ProductDetailsServiceService() {
		// TODO Auto-generated constructor stub
	}
	 
	@Override
	@HystrixCommand(fallbackMethod = "getFallbackProduct", ignoreExceptions = {DataNotFoundException.class})
	public List<ProductDetails> searchProductService(String prodName) throws DataNotFoundException{	
		List<ProductDetails> foundProduct = new ArrayList<ProductDetails>();
		try {	
			ResponseEntity<Product[]> fetchedProducts = restTemplate.getForEntity(Constants.ProductUrl + prodName, Product[].class);
			if(fetchedProducts.getStatusCode().equals(HttpStatus.OK)) {
				Product[] products = fetchedProducts.getBody();
				foundProduct = Arrays.asList(products).stream().map(product->{
					ResponseEntity<Inventory> inventory = restTemplate.getForEntity(Constants.InventoryUrl + product.getId(), Inventory.class);
					if(inventory.getStatusCode().equals(HttpStatus.OK)) {
						return new ProductDetails(product.getDescription(), product.getName(), inventory.getBody().getProductQty());
					}
					return null;
				}).collect(Collectors.toList());
				return foundProduct;
			}
		}catch(RestClientException ex) {
			ex.printStackTrace();
			throw new DataNotFoundException();
		}
		return foundProduct;
	}

	@Override
	public ProductDetails addProduct(ProductDetails product) throws Exception{
		try {
			Product productDetail = new Product();
			ProductDetails savedProductDetails = null;
			productDetail.setDescription(product.getDescription());
			productDetail.setName(product.getName());
			ResponseEntity<Product> savedProduct = restTemplate.postForEntity(Constants.AddProductUrl, productDetail, Product.class);
			log.debug("saved status : "+ savedProduct.getStatusCodeValue());
			if(savedProduct.getStatusCode().equals(HttpStatus.CREATED)) {
				log.debug("saving inventory");
				Inventory inventoryDetails = new Inventory();
				inventoryDetails.setId(savedProduct.getBody().getId());
				inventoryDetails.setProductQty(product.getProductQty());
				ResponseEntity<Inventory> savedInventory = restTemplate.postForEntity(Constants.AddInventorytUrl, inventoryDetails, Inventory.class);
				if(savedInventory.getStatusCode().equals(HttpStatus.CREATED)) {
					savedProductDetails = new ProductDetails(savedProduct.getBody().getDescription(), 
					savedProduct.getBody().getName(), savedInventory.getBody().getProductQty());
				}
			}
			return savedProductDetails;
		}catch (RestClientException ex) {
			throw new Exception();
		}
	}

	@Override
	public ResponseEntity<HttpStatus> deleteProduct(Integer prodId) throws Exception {
		try {
		      Map<String, Integer> params = new HashMap<String, Integer>();
		      params.put("id",prodId);
		      restTemplate.delete(Constants.DeleteProductUrl, params);
		      restTemplate.delete(Constants.DeleteInventoryUrl, params);
		      return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(RestClientException ex) {
			throw new Exception();
		}
	}
	
	public List<ProductDetails> getFallbackProduct(@PathVariable("name") String prodName) {
		List<ProductDetails> product = Arrays.asList(new ProductDetails("No Product", " " , 0));
		return product;
	}
}

