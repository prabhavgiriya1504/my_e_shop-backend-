//package com.ecommerceapi.service;
//
//import java.util.Iterator;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ecommerceapi.exception.ProductException;
//import com.ecommerceapi.model.Cart;
//import com.ecommerceapi.model.CartItems;
//import com.ecommerceapi.model.Product;
//import com.ecommerceapi.model.User;
//import com.ecommerceapi.repository.CartRepository;
//import com.ecommerceapi.request.AddItemRequest;
//
//@Service
//public class CartServiceImpl implements CartService {
//
//	@Autowired
//	private CartRepository cartRepo;
//	@Autowired
//	private CartItemService cartItemService;
//	@Autowired
//	private ProductService productService;
//	
//	@Override
//	public Cart createCart(User user) {
//		Cart cart = new Cart();
//		cart.setUser(user);
//		
//		return cartRepo.save(cart);
//	}
//
//	@Override
//	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
//		Cart cart = cartRepo.findByUserId(userId);
//		Product product = productService.findProductById(req.getProductId());
//		
//		CartItems isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
//		
//		if(isPresent == null) {
//			CartItems cartItem = new CartItems();
//			cartItem.setProduct(product);
//			cartItem.setCart(cart);
//			cartItem.setQuantity(req.getQuantity());
//			cartItem.setUserId(userId);
//			
//			
//			int price = req.getQuantity()*product.getDiscountedPrice();
//			cartItem.setPrice(price);
//			cartItem.setSize(req.getSize());
//			
//			CartItems createdCartItem = cartItemService.createCartItem(cartItem);
//			cart.getCartItem().add(createdCartItem);
//		}
//		return "item added to the cart.....";
//	}
//
//	@Override
//	public Cart findUserCart(Long userId) {
//		Cart cart = cartRepo.findByUserId(userId);
//		
//		if (cart == null) {
//	        throw new RuntimeException("Cart not found for user with id: " + userId);
//	    }
//
//	  
//		int totalPrice = 0;
//	    int totalDiscountedPrice = 0;
//	    int totalItem = 0;
//	    
//	    for(CartItems cartItem :cart.getCartItem()) {
//	    	totalPrice = totalPrice + cartItem.getPrice();
//	    	totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
//	    	totalItem = totalItem + cartItem.getQuantity();
//	    	
//	    }
//	    
//	    cart.setTotalPrice(totalPrice);
//	    cart.setTotalDiscountedPrice(totalDiscountedPrice);
//	    cart.setTotalItem(totalItem);
//	    
//		return cartRepo.save(cart);
//	}
//
//}



package com.ecommerceapi.service;

import java.util.Iterator;
import java.util.HashSet; // Or ArrayList, depending on your Cart.getCartItem() return type
import java.util.Collection; // To use a more generic type for the copy

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Use Spring's Transactional for consistency

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.CartItems;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.CartRepository;
import com.ecommerceapi.request.AddItemRequest;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	
	@Override
	
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		
		return cartRepo.save(cart);
	}

	@Override
	@Transactional 
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepo.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		
		CartItems isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent == null) {
			CartItems cartItem = new CartItems();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			
			int price = req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItems createdCartItem = cartItemService.createCartItem(cartItem);
			
			cart.getCartItem().add(createdCartItem); 
			
			
			cartRepo.save(cart); 
		} else {
            
        }
		return "item added to the cart.....";
	}

	@Override
	@Transactional 
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepo.findByUserId(userId);
		
		if (cart == null) {
	 throw new RuntimeException("Cart not found for user with id: " + userId);
	 }

	 	
        Collection<CartItems> cartItemsSnapshot = new HashSet<>(cart.getCartItem()); 

	 	int totalPrice = 0;
	 int totalDiscountedPrice = 0;
	 int totalItem = 0;
	 int discount = 0;
	 
	 
	 
          for(CartItems cartItem : cartItemsSnapshot) { 
        	  
	 	totalPrice = totalPrice + cartItem.getPrice();
	 	totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
	 	totalItem = totalItem + cartItem.getQuantity();
	 	 
	 	 
	 
	 }
	 
	 cart.setTotalPrice(totalPrice);
	 cart.setTotalDiscountedPrice(totalDiscountedPrice);
cart.setTotalItem(totalItem);
cart.setDiscount(totalPrice - totalDiscountedPrice);
	 
	return cartRepo.save(cart); 
	}

}
