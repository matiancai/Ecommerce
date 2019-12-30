package com.example.demo.controllers;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final Logger log = Logger.getLogger(OrderController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping("/submit/{userName}")
	public ResponseEntity<UserOrder> submit(@PathVariable String userName) {
		AppUser appUser = userRepository.findByUsername(userName);
		if(appUser == null) {
			log.info("OrderSubmit = failure username = " + userName);
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(appUser.getCart());
		orderRepository.save(order);
		log.info("OrderSubmit = success username = " + userName);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{userName}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String userName) {
		log.info("OrderHistorySearch =" + userName);
		AppUser appUser = userRepository.findByUsername(userName);
		if(appUser == null) {
			log.info("OrderHistorySearchResult = failure username = " + userName);
			return ResponseEntity.notFound().build();
		}
		log.info("OrderHistorySearchResult = success username = " + userName);
		return ResponseEntity.ok(orderRepository.findByAppUser(appUser));
	}
}
