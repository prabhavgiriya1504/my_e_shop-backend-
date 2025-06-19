package com.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.request.CreateProductRequest;
import com.ecommerceapi.response.ApiResponse;
import com.ecommerceapi.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

	@Autowired
	private ProductService prodService;
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){

	    Product product = prodService.createProduct(req);
	    return new ResponseEntity<Product>(product, HttpStatus.CREATED);

	}

	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{

	    prodService.deleteProduct(productId);
	    ApiResponse res = new ApiResponse();
	    res.setMessage("product deleted successfully");
	    res.setStatus(true);

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct(){

	    List<Product> products = prodService.findAllProducts();

	    return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable Long productId)
	throws ProductException{

	    Product product = prodService.updateProduct(productId, req);
	    return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){

	    for(CreateProductRequest product: req){
	        prodService.createProduct(product);
	    }
	    ApiResponse res = new ApiResponse();
	    res.setMessage("product created successfully");
	    res.setStatus(true);

	    return new ResponseEntity<>(res, HttpStatus.CREATED);
	}



}
