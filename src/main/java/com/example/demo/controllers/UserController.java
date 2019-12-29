package com.example.demo.controllers;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private CartRepository cartRepository;

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<AppUser> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<AppUser> findByUserName(@PathVariable String username) {
		System.err.println(username);
		AppUser appUser = userRepository.findByUsername(username);

		return appUser == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(appUser);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AppUser> createUser(@RequestBody CreateUserRequest createUserRequest) {
		AppUser appUser = new AppUser();
		appUser.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		appUser.setCart(cart);

		if(createUserRequest.getPassword().length() < 7 ||
			!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
			System.err.println("Error with appUser password. Cannot Create appUser: " + createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}

		appUser.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		userRepository.save(appUser);
		System.err.println(appUser);
		return ResponseEntity.ok(appUser);
	}
	
}
