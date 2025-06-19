package com.ecommerceapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapi.model.OrderItem;
import com.ecommerceapi.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
	    	
		return orderItemRepo.save(orderItem);
	}

}
