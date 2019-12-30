package com.example.demo.controllers;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private CartRepository cartRepository;

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<AppUser> findById(@PathVariable Long id) {
		log.info("UserIDSearch = " + id);
		Optional<AppUser> appUser = userRepository.findById(id);
		if(appUser.isPresent()){
			log.info("UserIdFound =  " + id);
			return ResponseEntity.ok(appUser.get());
		}else{
			log.info("UserIdNotFound =" + id);
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<AppUser> findByUserName(@PathVariable String username) {
		log.info("UserNameSearch =  " + username);
		AppUser appUser = userRepository.findByUsername(username);
		if(appUser!=null){
			log.info("UserNameFound =  " + username);
			return ResponseEntity.ok(appUser);
		}else{
			log.info("UserNameNotFound = " + username);
			return ResponseEntity.notFound().build();
		}
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
			log.info("UserCreation = failure Username = " + createUserRequest.getUsername() );
			return ResponseEntity.badRequest().build();
		}

		appUser.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		userRepository.save(appUser);
		log.info("UserCreation = success UserName =" + createUserRequest.getUsername());
		System.err.println(appUser);
		return ResponseEntity.ok(appUser);
	}
	
}
