package com.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.User;
import com.ecommerceapi.service.UserService;

@RestController
@RequestMapping("/api/users") 
public class UserController {

  @Autowired
  private UserService userService;
  
  //we can get the users detail , when they login
  @GetMapping("/profile")
  public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization" )String jwt)throws UserException{
    User user = userService.findUserProfileByJwt(jwt);
    return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
  }
  
  
}

