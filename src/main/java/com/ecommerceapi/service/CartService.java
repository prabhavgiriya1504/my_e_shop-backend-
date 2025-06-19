package com.ecommerceapi.service;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.User;
import com.ecommerceapi.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);
	
	public String addCartItem(Long userId , AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userId);	
}
