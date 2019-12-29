package com.example.demo.controllers;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		AppUser appUser = userRepository.findByUsername(username);
		if(appUser == null) {
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(appUser.getCart());
		orderRepository.save(order);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		AppUser appUser = userRepository.findByUsername(username);
		if(appUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByAppUser(appUser));
	}
}
