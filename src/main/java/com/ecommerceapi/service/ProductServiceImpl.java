package com.ecommerceapi.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Category;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.repository.CategoryRepository;
import com.ecommerceapi.repository.ProductRepository;
import com.ecommerceapi.request.CreateProductRequest;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository proRepo;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private CategoryRepository categoryRepo; 
	
	@Override
	public Product createProduct(CreateProductRequest req) {
		Category topLavel = categoryRepo.findByName(req.getTopLavelCategory());
		
		if(topLavel == null) {
			Category topLavelCategory = new Category();
			topLavelCategory.setName(req.getTopLavelCategory());
			topLavelCategory.setLevel(1);
			
			topLavel = categoryRepo.save(topLavelCategory);
		}
		Category secondLavel = categoryRepo.findByNameAndParent(req.getSecondLavelCategory(), topLavel);
		
		if(secondLavel == null) {
			Category secondLavelCategory = new Category();
			secondLavelCategory.setName(req.getSecondLavelCategory());
			secondLavelCategory.setParentCategory(topLavel);
			secondLavelCategory.setLevel(2);
			
			secondLavel = categoryRepo.save(secondLavelCategory);
		}
		
		Category thirdLavel = categoryRepo.findByNameAndParent(req.getThirdLavelCategory(), secondLavel);
		
		if(thirdLavel == null) {
			Category thirdLavelCategory = new Category();
			thirdLavelCategory.setName(req.getThirdLavelCategory());
			thirdLavelCategory.setParentCategory(secondLavel);
			thirdLavelCategory.setLevel(3);
			
			thirdLavel = categoryRepo.save(thirdLavelCategory);
			
		}
		
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPercent(req.getDiscountPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLavel);
		product.setCreatedAt(LocalDateTime.now());

		Product savedProduct = proRepo.save(product);
		
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSizes().clear();
		proRepo.delete(product);
	   return "product deleted successfully.....";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product = findProductById(productId);
		
		if(req.getQuantity() != 0) {
			product.setQuantity(req.getQuantity());
		}
		return proRepo.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt = proRepo.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("product not found with the id : "+id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		return null;
				
	}
	@Override
	public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice,
	                                   Integer maxPrice, Integer minDiscount, String stock, Integer pageNumber, Integer pageSize) {

	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    List<Product> products = proRepo.filterProducts(category, minPrice, maxPrice, minDiscount);

	    if (color != null && !color.isEmpty()) {
	        products = products.stream()
	                .filter(p -> color.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
	                .collect(Collectors.toList());
	    }

	    if (stock != null) {
	        if (stock.equals("in_stock")) {
	            products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
	        } else if (stock.equals("out_of_stock")) {
	            products = products.stream().filter(p -> p.getQuantity() <= 0).collect(Collectors.toList());
	        }
	    }

	    int startIndex = (int) pageable.getOffset();
	    int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

	    if (startIndex > products.size()) {
	        return new PageImpl<>(Collections.emptyList(), pageable, products.size());
	    }

	    List<Product> pageContent = products.subList(startIndex, endIndex);
	    return new PageImpl<>(pageContent, pageable, products.size());
	}


	@Override
	public List<Product> findAllProducts() {
		
		return proRepo.findAll();
	}

}
