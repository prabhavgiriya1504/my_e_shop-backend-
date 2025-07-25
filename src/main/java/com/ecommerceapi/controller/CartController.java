package com.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.ProductException;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.User;
import com.ecommerceapi.request.AddItemRequest;
import com.ecommerceapi.response.ApiResponse;
import com.ecommerceapi.service.CartService;
import com.ecommerceapi.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    //user can get his cart, if he is currently logged in
    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getUid());
        
        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }
    
    //user can add items to cart
    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
          @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
      User user = userService.findUserProfileByJwt(jwt);

      cartService.addCartItem(user.getUid(), req);

      ApiResponse res = new ApiResponse();
      res.setMessage("item added to cart");
      res.setStatus(true);
      return new ResponseEntity<>(res, HttpStatus.OK);
   }
    
    

 }
