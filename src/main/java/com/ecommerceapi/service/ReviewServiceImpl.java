package com.ecommerceapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.model.Review;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.ProductRepository;
import com.ecommerceapi.repository.ReviewRepository;
import com.ecommerceapi.request.ReviewRequest;
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private ProductService prodService;
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = prodService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setProduct(product);
		review.setUser(user);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
				return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		return reviewRepo.getAllProductsReview(productId);
	}

}
