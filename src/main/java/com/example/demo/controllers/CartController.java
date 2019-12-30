package com.example.demo.controllers;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/cart")
public class CartController {

	private static final Logger log = Logger.getLogger(CartController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		AppUser appUser = userRepository.findByUsername(request.getUsername());
		if(appUser == null) {
			log.info("AddItem = failure username = " + request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			log.info("AddItem = failure username = " + request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Cart cart = appUser.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.addItem(item.get()));
		cartRepository.save(cart);
		log.info("AddItem = success username = " + request.getUsername());
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		AppUser appUser = userRepository.findByUsername(request.getUsername());
		if(appUser == null) {
			log.info("RemoveItem = failure username = " + request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			log.info("RemoveItem = failure username = " + request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = appUser.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.removeItem(item.get()));
		cartRepository.save(cart);
		log.info("RemoveItem = success username = " + request.getUsername());
		return ResponseEntity.ok(cart);
	}
}
