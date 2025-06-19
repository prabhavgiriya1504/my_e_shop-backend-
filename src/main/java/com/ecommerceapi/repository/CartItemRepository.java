package com.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.CartItems;
import com.ecommerceapi.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {

	@Query("SELECT ci FROM CartItems ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
	public CartItems isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product , @Param("size")String size , @Param("userId")Long userId);
}
