package com.ecommerceapi.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.config.JwtProvider;
import com.ecommerceapi.exception.UserException;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.UserRepository;
import com.ecommerceapi.request.LoginRequest;
import com.ecommerceapi.response.AuthResponse;
import com.ecommerceapi.service.CartService;
import com.ecommerceapi.service.CustomUserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetailServiceImpl service;
	@Autowired
	private CartService cartService;
		
	// user can signup by providing , email, firstname, lastname, password 
	@PostMapping(value = "/signup" , produces = "application/json")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user)throws Exception{
		String email = user.getEmail();
		String password = user.getPassword();
		String firstString = user.getFirstName();
		String lastString = user.getLastName();
		
		User isEmailExist = repo.findByEmail(email);
		
		if(isEmailExist != null) {
			throw new UserException("email is already registerd with us "+email);
		}
		User newUser = new User();
		
		newUser.setFirstName(firstString);
		newUser.setLastName(lastString);
		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setCreatedAt(LocalDateTime.now());
		
		User savedUser = repo.save(newUser);
		Cart cart = cartService.createCart(savedUser); 
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail() , savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("sign-up successfully....");
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}
	
	//user can signin , by providing email and password
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
		String userName = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(userName , password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("sign-in success......|||||");
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}
	
	//method to authenticate user
	public Authentication authenticate(String userName , String password) {
		UserDetails userDetail = service.loadUserByUsername(userName);
		if(userDetail == null) {
			throw new BadCredentialsException("invalid username ...");
			
		}
		if(!passwordEncoder.matches(password, userDetail.getPassword())) {
			throw new BadCredentialsException("invalid password ....");
		}
		return new UsernamePasswordAuthenticationToken(userDetail, null ,userDetail.getAuthorities());
	}
}
