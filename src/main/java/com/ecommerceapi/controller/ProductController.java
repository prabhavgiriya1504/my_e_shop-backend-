package com.ecommerceapi.controller;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService prodService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(
	        @RequestParam(required = false) String category,
	        @RequestParam(required = false) List<String> color,
	        @RequestParam(required = false) List<String> size,
	        @RequestParam(required = false) Integer minPrice,
	        @RequestParam(required = false) Integer maxPrice,
	        @RequestParam(required = false) Integer minDiscount,
	        @RequestParam(required = false) String stock,
	        @RequestParam Integer pageNumber,
	        @RequestParam Integer pageSize
	) {
	    Page<Product> res = prodService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, stock, pageNumber, pageSize);
	    return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}

	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(
	        @PathVariable Long productId) throws ProductException {

	    Product product = prodService.findProductById(productId);
	    return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
	}
}
