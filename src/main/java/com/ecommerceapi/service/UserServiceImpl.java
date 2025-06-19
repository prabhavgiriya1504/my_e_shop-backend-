package com.ecommerceapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapi.config.JwtProvider;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User>user = repo.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
        throw new UserException("user not found with the id : "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = repo.findByEmail(email);
		
		if(user == null) {
			throw new UserException("user not found with email : "+email);
			
		}
		return user;
	}

}
