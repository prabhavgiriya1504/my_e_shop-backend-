package com.ecommerceapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Ratings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id" , nullable = false)
	public User user;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id" , nullable = false)
	private Product product;
	
	@Column(name = "rating")
	private double rating;
	
	private LocalDateTime createdAt;
	
	public Ratings() {
		//ddkdmm
	}
}
