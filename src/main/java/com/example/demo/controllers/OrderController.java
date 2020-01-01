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
import java.util.Optional;

import static com.example.demo.model.persistence.UserOrder.createFromCart;

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
		try{
			Optional<AppUser> appUser = userRepository.findByUsername(userName);
			if(!appUser.isPresent()) {
                throw new ApiException(ExceptionTypes.SUBMITORDER, userName);
			}

			UserOrder order = UserOrder.createFromCart(appUser.get().getCart());
			order.setAppUser(appUser.get()); /*Is this line necessary?*/
			if(order != null){
				orderRepository.save(order);
				log.info("OrderSubmit = success username = " + userName);
				return ResponseEntity.ok(order);
			}else{
				throw new ApiException(ExceptionTypes.SUBMITORDER, userName);
			}

		}catch(ApiException a){
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/history/{userName}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String userName) {
		try{
			Optional<AppUser> appUser = userRepository.findByUsername(userName);
			if(!appUser.isPresent()) {
				throw new ApiException(ExceptionTypes.ORDERHISTORY, userName);
			}
			log.info("OrderHistorySearchResult = success username = " + userName);
			return ResponseEntity.ok(orderRepository.findByAppUser(appUser.get()));
		}catch(ApiException a){
			return ResponseEntity.notFound().build();
		}
	}
}
