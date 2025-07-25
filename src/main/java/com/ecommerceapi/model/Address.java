

package com.ecommerceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter; // Add specific Lombok annotations
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor; // Optional: if you need a constructor with all fields
import lombok.ToString; // Explicitly import ToString
import lombok.EqualsAndHashCode; // Explicitly import EqualsAndHashCode

import java.util.Objects; // Import for Objects.equals and Objects.hash

@Entity
@Getter // Generates getters for all fields
@Setter // Generates setters for all fields
@NoArgsConstructor // Generates the no-argument constructor
// @Data // REMOVED: Replaced by @Getter, @Setter, @ToString, @EqualsAndHashCode
@ToString(exclude = "user") // Exclude 'user' from toString() to prevent StackOverflowError
@EqualsAndHashCode(of = "id") // Ensures equals/hashCode only use the primary key (id)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "street_address")
	private String streetAddress;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore // Good for JSON serialization to prevent infinite recursion in JSON
	private User user; // This is the field causing the toString() recursion
	
	private String mobile;
	
	// You can remove this explicit constructor if @NoArgsConstructor is used
	// public Address() {
	// 	//non-parametrized constructor
	// }
}
