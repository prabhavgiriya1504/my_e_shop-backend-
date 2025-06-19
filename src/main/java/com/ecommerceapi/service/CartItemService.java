package com.ecommerceapi.service;

import com.ecommerceapi.exception.CartItemException;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.CartItems;
import com.ecommerceapi.model.Product;

public interface CartItemService {

	public CartItems createCartItem(CartItems cartItem);
	public CartItems updateCartItem(Long userId , Long id , CartItems cartItem)throws CartItemException,UserException;
	public CartItems isCartItemExist(Cart cart , Product product , String size , Long userId);
	public void removeCartItem(Long userId , Long cartItemId)throws CartItemException,UserException; 	 	
	public CartItems findCartItemById(Long cartItmeId)throws CartItemException;
}
