package com.ecommerceapi.service;

import java.util.List;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Review;
import com.ecommerceapi.model.User;
import com.ecommerceapi.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req , User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId);
}
