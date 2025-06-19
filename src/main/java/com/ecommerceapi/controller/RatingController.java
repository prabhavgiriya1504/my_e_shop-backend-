package com.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
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
import com.ecommerceapi.model.Ratings;
import com.ecommerceapi.model.User;
import com.ecommerceapi.request.RatingRequest;
import com.ecommerceapi.service.RatingService;
import com.ecommerceapi.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {


	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Ratings> 	createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
	User user = userService.findUserProfileByJwt(jwt);
	Ratings rating = ratingService.createRating(req, user);
	return new ResponseEntity<Ratings>(rating,HttpStatus.CREATED);
	}
	

	@GetMapping("/product/{productid}")
	public ResponseEntity<List<Ratings>> getProductsRating(@PathVariable Long productid, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
	
	User user = userService.findUserProfileByJwt(jwt);
	List<Ratings> ratings = ratingService.getProductRating(productid);
	return new ResponseEntity<>(ratings, HttpStatus.CREATED);
	}
}
