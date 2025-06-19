package com.ecommerceapi.service;

import java.util.List;

import com.ecommerceapi.exception.OrderException;
import com.ecommerceapi.model.Address;
import com.ecommerceapi.model.Order;
import com.ecommerceapi.model.User;

public interface OrderService {

	public Order createOrder(User user , Address shippddress);
	
	public Order findOrderById(Long orderId) throws OrderException;

	public List<Order> usersOrderHistory(Long userId);

	public Order placedOrder(Long orderId) throws OrderException;

	public Order confirmedOrder(Long orderId) throws OrderException;

	public Order shippedOrder(Long orderId) throws OrderException;

	public Order deliveredOrder(Long orderId) throws OrderException;

	public Order cancledOrder(Long orderId) throws OrderException;

	public List<Order> getAllOrders();

	public void deleteOrder(Long orderId) throws OrderException;

}
