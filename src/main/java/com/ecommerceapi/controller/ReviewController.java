package com.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.Review;
import com.ecommerceapi.model.User;
import com.ecommerceapi.request.ReviewRequest;
import com.ecommerceapi.service.ReviewService;
import com.ecommerceapi.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {


	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;
	
	//users can provide review , by providing the details
	@PostMapping("/create")
	public ResponseEntity<Review> createReviewReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		Review review=reviewService.createReview(req, user);
		
		return new ResponseEntity<>(review,HttpStatus.CREATED);
	}
	

	//we can show the product's all reviews to user in product_detail page
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productid) throws UserException, ProductException{
	  List<Review>reviews = reviewService.getAllReview(productid);
	    return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
	}
}
