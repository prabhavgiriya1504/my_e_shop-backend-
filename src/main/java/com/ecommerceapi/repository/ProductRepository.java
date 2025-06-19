package com.ecommerceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerceapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p " +
		       "WHERE (:category IS NULL OR p.category.name = :category) " +
		       "AND ((:minPrice IS NULL OR :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
		       "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount)")
		List<Product> filterProducts(@Param("category") String category,
		                             @Param("minPrice") Integer minPrice,
		                             @Param("maxPrice") Integer maxPrice,
		                             @Param("minDiscount") Integer minDiscount);



}
