package com.ecommerceapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapi.exception.OrderException;
import com.ecommerceapi.model.Address;
import com.ecommerceapi.model.Cart;
import com.ecommerceapi.model.CartItems;
import com.ecommerceapi.model.Order;
import com.ecommerceapi.model.OrderItem;
import com.ecommerceapi.model.User;
import com.ecommerceapi.repository.AddressRepository;
import com.ecommerceapi.repository.CartRepository;
import com.ecommerceapi.repository.OrderItemRepository;
import com.ecommerceapi.repository.OrderRepository;
import com.ecommerceapi.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public Order createOrder(User user, Address shippAddress) {
		shippAddress.setUser(user);
	    Address address= addressRepository.save(shippAddress);
	    user.getAddress().add(address);
	    userRepository.save(user);

	    Cart cart=cartService.findUserCart(user.getUid());
	    List<OrderItem> orderItems=new ArrayList<>();

	    for(CartItems item : cart.getCartItem()) {
	        OrderItem orderItem=new OrderItem();

	        orderItem.setPrice(item.getPrice());
	        orderItem.setProduct(item.getProduct());
	        orderItem.setQuantity(item.getQuantity());	
	        orderItem.setSize(item.getSize());
	        orderItem.setUserId(item.getUserId());
	        orderItem.setDiscountedPrice(item.getDiscountedPrice());
	        
	        OrderItem createdOrderItem = orderItemRepository.save(orderItem);
	        
	        orderItems.add(createdOrderItem);
	    }
	    
	    Order createdOrder = new Order();
	    createdOrder.setUser(user);
	    createdOrder.setOrderItems(orderItems);
	    createdOrder.setTotalPrice(cart.getTotalPrice());
	    createdOrder.setDiscountedPrice(cart.getTotalDiscountedPrice());
	    createdOrder.setDiscount(cart.getDiscount());
	    createdOrder.setTotalItem(cart.getTotalItem());

	    createdOrder.setShippingAddress(address);
	    createdOrder.setOrderDate(LocalDateTime.now());
	    createdOrder.setOrderStatus("PENDING");
	    createdOrder.getPaymentDetail().setStatus("PENDING");
	    createdOrder.setCreatedAt(LocalDateTime.now());

	    Order savedOrder = orderRepository.save(createdOrder);
	    
	    for(OrderItem item : orderItems) {
	    	item.setOrder(savedOrder);
	    	orderItemRepository.save(item);
	    }

	        return savedOrder;
	}
	
	public Order placedOrder(Long orderId) throws OrderException {
	    Order order = findOrderById(orderId);
	    order.setOrderStatus("PLACED");
	    order.getPaymentDetail().setStatus("COMPLETED");
	    return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
	    Order order = findOrderById(orderId);
	    order.setOrderStatus("CONFIRMED");

	    return orderRepository.save(order);
	}


	

	
	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
	    Order order = findOrderById(orderId);
	    order.setOrderStatus("SHIPPED");
	    return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
	    Order order = findOrderById(orderId);
	    order.setOrderStatus("DELIVERED");
	    return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
	    Order order = findOrderById(orderId);
	    order.setOrderStatus("CANCELLED");
	    return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
	    Optional<Order> opt = orderRepository.findById(orderId);

	    if (opt.isPresent()) {
	        return opt.get();
	    }
	    throw new OrderException("order not exist with id " + orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
	    List<Order> orders = orderRepository.getUsersOrders(userId);
	    return orders;
	}

	@Override
	public List<Order> getAllOrders() {
	    return orderRepository.findAll();
	}

	

	
	@Override
	public void deleteOrder(Long orderId) throws OrderException {

		Order order = findOrderById(orderId);
		
		orderRepository.deleteById(orderId);
	}

}
