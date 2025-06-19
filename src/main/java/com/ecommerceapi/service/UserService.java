package com.ecommerceapi.service;

import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException;
	public User findUserProfileByJwt(String jwt) throws UserException;
}
