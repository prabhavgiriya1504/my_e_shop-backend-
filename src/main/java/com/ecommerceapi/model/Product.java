package com.ecommerceapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects; // Import for Objects.equals and Objects.hash
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
// import lombok.Data; // REMOVE THIS LINE
import lombok.Getter; // Add specific Lombok annotations
import lombok.Setter;
import lombok.NoArgsConstructor; // Add if you need a no-arg constructor
import lombok.AllArgsConstructor; // Add if you need an all-arg constructor

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity

public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	private String title;
	
	private String description;
	
	private int price;
	
	@Column(name = "discounted_price")
	private int discountedPrice;
	
	@Column(name = "discounted_percent")
	private int discountPercent;
	
	private int quantity;
	
	private String brand;
	
	private String color;
	
	@Column(name = "image_url")
	private String imageUrl;
	@Embedded
	@ElementCollection
	@Column(name = "sizes")
	private Set<Size>sizes = new HashSet<>(); // Make sure Size class also has correct equals/hashCode

	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Ratings> ratings = new ArrayList<>();
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Review> review = new ArrayList<>();
	
	@Column(name = "num_ratings")
	private int numRatings;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category; 
	
	private LocalDateTime createdAt;

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        
        return id != null ? Objects.hash(id) : 0;
    }
}