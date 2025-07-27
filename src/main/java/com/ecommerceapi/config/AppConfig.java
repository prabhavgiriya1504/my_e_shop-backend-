//package com.ecommerceapi.config;
//
//
//import java.net.URI;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.apache.tomcat.util.file.ConfigurationSource;
//import org.apache.tomcat.util.file.ConfigurationSource.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.web.config.EnableSpringDataWebSupport;
//import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
//@Configuration 
//public class AppConfig {
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ 
//		
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() 
//		.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
//		.addFilterBefore(new JWTValidator(), BasicAuthenticationFilter.class).csrf().disable()
//		.cors().configurationSource(new CorsConfigurationSource() {
//			
//			@Override
//			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//				CorsConfiguration cfg = new CorsConfiguration();
//				
//				cfg.setAllowedOrigins(Arrays.asList(
//						"http://localhost:3000",
//						"https://my-e-shop-five.vercel.app"
//						));
//				cfg.setAllowedMethods(Collections.singletonList("*"));
//				cfg.setAllowCredentials(true);
//				cfg.setAllowedHeaders(Collections.singletonList("*"));
//				cfg.setExposedHeaders(Arrays.asList("Authorization"));
//				cfg.setMaxAge(3600L);
//				
//				return cfg;
//			}
//		})
//		.and().httpBasic().and().formLogin();
//		
//		return http.build();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}



package com.ecommerceapi.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

// --- Added Logging Imports ---
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// --- End Added Logging Imports ---

@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@Configuration
public class AppConfig {

    // --- Added Logger Instance ---
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    // --- End Added Logger Instance ---

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
		.addFilterBefore(new JWTValidator(), BasicAuthenticationFilter.class).csrf().disable()
		.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                // --- Added Logging Statements Here ---
                logger.info("CORS Configuration Source invoked for request URI: {}", request.getRequestURI());
                logger.info("Request Method: {}", request.getMethod());
                logger.info("Request Origin: {}", request.getHeader("Origin"));
                // --- End Added Logging Statements ---

				CorsConfiguration cfg = new CorsConfiguration();
				
				cfg.setAllowedOrigins(Arrays.asList(
						"http://localhost:3000",
						"https://my-e-shop-five.vercel.app"
						));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);

                // --- Optional: Log the resulting configuration details if desired ---
                logger.info("Allowed Origins configured: {}", cfg.getAllowedOrigins());
                logger.info("Allowed Methods configured: {}", cfg.getAllowedMethods());
                logger.info("Allow Credentials configured: {}", cfg.getAllowCredentials());
                // --- End Optional Logging ---
				
				return cfg;
			}
		})
		.and().httpBasic().and().formLogin();
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}