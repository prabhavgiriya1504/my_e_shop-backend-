package com.ecommerceapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uid;
	
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String role;
	private String mobile;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address>address = new ArrayList<>(); 
	
	@Embedded
	@ElementCollection
	@CollectionTable(name = "payment_information" , joinColumns = @JoinColumn(name = "user_id"))
	private List<PaymentInformation>paymentInfo = new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Ratings>rating = new ArrayList<>();
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
	private List<Review>review = new ArrayList<>();
	
	private LocalDateTime createdAt;
	
	public User() {
		//non parametrized constructor
	}
	
}
