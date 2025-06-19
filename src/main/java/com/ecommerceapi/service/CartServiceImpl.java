package com.ecommerceapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		}
		return "item added to the cart.....";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepo.findByUserId(userId);
		int totalPrice = 0;
	    int totalDiscountedPrice = 0;
	    int totalItem = 0;
	    
	    for(CartItems cartItem :cart.getCartItem()) {
	    	totalPrice = totalPrice + cartItem.getPrice();
	    	totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
	    	totalItem = totalItem + cartItem.getQuantity();
	    	
	    }
	    
	    cart.setTotalPrice(totalPrice);
	    cart.setTotalDiscountedPrice(totalDiscountedPrice);
	    cart.setTotalDiscountedPrice(totalDiscountedPrice);
	    
		return cartRepo.save(cart);
	}

}
