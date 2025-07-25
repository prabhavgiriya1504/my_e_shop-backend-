package com.ecommerceapi.model;

import java.util.HashSet;
import java.util.Objects; // Import for Objects.equals and Objects.hash
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
// import lombok.Data; // REMOVE THIS LINE
import lombok.Getter; // Add specific Lombok annotations
import lombok.Setter;
import lombok.NoArgsConstructor; // Add if you need a no-arg constructor
import lombok.AllArgsConstructor; // Add if you need an all-arg constructor

@Getter // Generate getters
@Setter // Generate setters
@NoArgsConstructor // Generate a no-argument constructor (useful for JPA)
@AllArgsConstructor // Generate a constructor with all fields (optional, but often useful)
@Entity
// @Data // IMPORTANT: Make sure this line is REMOVED or commented out
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id" ,nullable = false)
	private User user; // Ensure User class also has correct equals/hashCode
	
	@OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL ,orphanRemoval = true)
	// @Column(name = "cart_items") // This annotation is incorrect for a @OneToMany mapping
	private Set<CartItems>cartItem = new HashSet<>(); // Always initialize collections!
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "total_item")
	private int totalItem;
	
	private int totalDiscountedPrice;
	
	private int discount;

    // --- IMPORTANT: Manually implement equals() and hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Use getClass() to be safe with Hibernate proxies
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        // Equality should primarily be based on the ID for persisted entities.
        // For new (transient) entities where id is null, they are typically
        // only equal if they are the exact same object instance.
        return id != null && Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        // Hash code should be consistent with equals.
        // For persisted entities, use ID.
        // For transient entities (id == null), returning 0 or a constant
        // is a common pattern to avoid issues, though it can impact HashSet performance
        // if many new objects are added without IDs.
        return id != null ? Objects.hash(id) : 0;
    }

    // --- Helper method for bidirectional relationship management ---
    // This is a good practice to ensure both sides of the relationship are set when adding items
    public void addCartItem(CartItems item) {
        if (item != null) {
            cartItem.add(item);
            item.setCart(this); // Set the back-reference
        }
    }

    public void removeCartItem(CartItems item) {
        if (item != null) {
            cartItem.remove(item);
            item.setCart(null); // Clear the back-reference
        }
    }
}