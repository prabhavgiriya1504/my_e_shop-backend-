package com.ecommerceapi.service;

import java.util.List;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Ratings;
import com.ecommerceapi.model.User;
import com.ecommerceapi.request.RatingRequest;

public interface RatingService {

	public Ratings createRating(RatingRequest req , User user)throws ProductException;
	
	public List<Ratings> getProductRating(Long productId);
}
