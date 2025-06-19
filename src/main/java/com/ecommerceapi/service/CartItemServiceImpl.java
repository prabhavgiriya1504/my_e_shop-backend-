package com.ecommerceapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapi.exception.CartItemException;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.CartItems;
import com.ecommerceapi.model.Product;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.CartItemRepository;
import com.ecommerceapi.repository.CartRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public CartItems createCartItem(CartItems cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItems createdCartItem = cartItemRepo.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItems updateCartItem(Long userId, Long id, CartItems cartItem) throws CartItemException, UserException {
		CartItems item = findCartItemById(id);
		User user = userService.findUserById(item.getUserId());
		
		if(user.getUid().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		return cartItemRepo.save(item);
	}

	@Override
	public CartItems isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItems cartItem = cartItemRepo.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

		CartItems cartItem = findCartItemById(cartItemId);
		
		User user = userService.findUserById(cartItem.getUserId());
		
		User reqUser = userService.findUserById(userId);
		
		if(user.getUid().equals(reqUser.getUid())) {
			cartItemRepo.deleteById(cartItemId);	
		}else {
			throw new UserException("you can't remove amother users item .....");
		}
	}

	@Override
	public CartItems findCartItemById(Long cartItmeId) throws CartItemException {
		Optional<CartItems>opt = cartItemRepo.findById(cartItmeId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cart item not found with id :"+cartItmeId);
	}

}
