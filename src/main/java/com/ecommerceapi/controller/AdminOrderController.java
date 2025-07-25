package com.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapi.exception.OrderException;
import com.ecommerceapi.model.Order;
import com.ecommerceapi.response.ApiResponse;
import com.ecommerceapi.response.AuthResponse;
import com.ecommerceapi.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	

	//admin to get orders 
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
	    List<Order> orders=orderService.getAllOrders();
	    return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
	}

	//admin will get confirmedorders
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
	    @RequestHeader("Authorization") String jwt) throws OrderException {
	    Order order=orderService.confirmedOrder(orderId);
	    
	    return new ResponseEntity<>(order , HttpStatus.OK);
	}

	//admin will get the shipped orders
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order>shippedOrderHandler(@PathVariable Long orderId ,@RequestHeader("Authorization")String jwt)throws OrderException{
		Order order = orderService.shippedOrder(orderId);
		
		return new ResponseEntity<>(order , HttpStatus.OK);
	}
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order>deliverOrderHandler(@PathVariable Long orderId ,@RequestHeader("Authorization")String jwt)throws OrderException{
		Order order = orderService.deliveredOrder(orderId);
		
		return new ResponseEntity<>(order , HttpStatus.OK);
	}
	
	//admin will get the cancel orders
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order>cancelOrderHandler(@PathVariable Long orderId ,@RequestHeader("Authorization")String jwt)throws OrderException{
		Order order = orderService.cancledOrder(orderId);
		
		return new ResponseEntity<>(order , HttpStatus.OK);
	}
	
	//admin can see deleted orders
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse>deleteOrderHandler(@PathVariable Long orderId ,@RequestHeader("Authorization")String jwt)throws OrderException{
		 orderService.deleteOrder(orderId);
		
		 ApiResponse res = new ApiResponse();
		 res.setMessage("order deleted successfully ...."); 
		 res.setStatus(true); 
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	
	
}
