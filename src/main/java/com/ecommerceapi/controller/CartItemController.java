package com.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.CartItemException;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.CartItems;
import com.ecommerceapi.model.User;
import com.ecommerceapi.response.ApiResponse;
import com.ecommerceapi.service.CartItemService;
import com.ecommerceapi.service.UserService;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(user.getUid(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("item deleted from cart");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItems> updateCartItem(
            @RequestBody CartItems cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        CartItems updatedCartItem = cartItemService.updateCartItem(user.getUid(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

}
