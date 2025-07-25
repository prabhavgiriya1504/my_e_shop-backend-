package com.ecommerceapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Import for Objects.equals and Objects.hash

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Import specific Lombok annotations
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor; // Optional: If you need a constructor with all fields
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="orders")
@Getter // Generates getters for all fields
@Setter // Generates setters for all fields
@NoArgsConstructor // Generates the no-argument constructor
// @Data // REMOVED: Replaced by @Getter, @Setter, @ToString, @EqualsAndHashCode
@ToString(exclude = {"user", "orderItems", "shippingAddress"}) // Exclude fields that cause circularity
@EqualsAndHashCode(of = "id") // Ensures equals/hashCode only use the primary key (id)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "order_id")
	private String orderId;
	
	@ManyToOne
	@JsonIgnore
	
	private User user; 
	
	@OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
	
	private List<OrderItem>orderItems = new ArrayList<>(); 
	
	private LocalDateTime orderDate;
	
	private LocalDateTime deliveryDate;
	
	@OneToOne
	 
	private Address shippingAddress; 
	
	@Embedded
	private PaymentDetails paymentDetail = new PaymentDetails();
	
	private double totalPrice;
	
	private Integer discountedPrice;
	private Integer discount;
	private String orderStatus;
	private int totalItem;
	private LocalDateTime createdAt;
	
	
}