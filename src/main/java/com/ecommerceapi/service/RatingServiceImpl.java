package com.ecommerceapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.model.Ratings;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.RatingRepository;
import com.ecommerceapi.request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private ProductService prodService;
	
	@Override
	public Ratings createRating(RatingRequest req, User user) throws ProductException {
		Product product = prodService.findProductById(req.getProductId());
		
		Ratings rating = new Ratings();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepo.save(rating);
	}

	@Override
	public List<Ratings> getProductRating(Long productId) {
		
		return ratingRepo.getAllProductsRating(productId);
	}

}
