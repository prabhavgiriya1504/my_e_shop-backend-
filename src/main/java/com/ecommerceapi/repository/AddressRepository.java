package com.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerceapi.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address , Long> {

}
