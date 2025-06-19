package com.ecommerceapi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.request.CreateProductRequest;

public interface ProductService {

	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId)throws ProductException;
	
	public Product updateProduct(Long productId , Product product)throws ProductException;
	
	public Product findProductById(Long id)throws ProductException;
	
	public List<Product>findProductByCategory(String category);
	
	public Page<Product>getAllProduct(String category , List<String>color , List<String>sizes , Integer minPrice , Integer maxPrice , Integer minDiscount ,String stock , Integer pageNumber , Integer pageSize);

	public List<Product> findAllProducts();

	
	
}
