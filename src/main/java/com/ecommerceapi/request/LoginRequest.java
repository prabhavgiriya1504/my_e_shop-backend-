package com.ecommerceapi.request;

import lombok.Data;

@Data
public class LoginRequest {

	private String email;
    private String password;
    
    public LoginRequest() {
    	//non-para const....
    }
}
