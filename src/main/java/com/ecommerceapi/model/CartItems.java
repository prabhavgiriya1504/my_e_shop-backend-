package com.ecommerceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
// import lombok.Data; // REMOVE OR REPLACE WITH SPECIFIC LOMBOK ANNOTATIONS
import lombok.Getter; // Use specific getters
import lombok.Setter; // Use specific setters
import lombok.NoArgsConstructor; // If you need a no-arg constructor
import lombok.AllArgsConstructor; // If you need an all-arg constructor

import java.util.Objects; // For Objects.equals and Objects.hash

@Getter // Generate getters
@Setter // Generate setters
@NoArgsConstructor // Generate a no-argument constructor (useful for JPA)
@AllArgsConstructor // Generate a constructor with all fields (optional, but often useful)
@Entity
// @Data // IMPORTANT: REMOVE THIS LINE OR COMMENT IT OUT
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Cart cart; 

	@ManyToOne
	private Product product; 

	private String size;
	private int quantity;
	private Integer price;
	private Integer discountedPrice;
	private Long userId;

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        CartItems other = (CartItems) o;
       
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        
        return id != null ? Objects.hash(id) : 0; 
    }
}