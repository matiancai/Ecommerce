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

	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private CartRepository cartRepository;

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<AppUser> findById(@PathVariable Long id) {
		try{
			log.info("UserIDSearch = " + id);
			Optional<AppUser> appUser = userRepository.findById(id);
			if(appUser.isPresent()){
				log.info("UserIdFound =  " + id);
				return ResponseEntity.ok(appUser.get());
			}else{
				throw new ApiException(ExceptionTypes.SEARCHUSER, id.toString());
			}
		}catch(ApiException a){
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<AppUser> findByUserName(@PathVariable String username) {
		try{
			log.info("UserNameSearch =  " + username);
			AppUser appUser = userRepository.findByUsername(username);
			if(appUser!=null){
				log.info("UserNameFound =  " + username);
				return ResponseEntity.ok(appUser);
			}else{
				throw new ApiException(ExceptionTypes.SEARCHUSER, username);
			}

		}catch(ApiException a){
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<AppUser> createUser(@RequestBody CreateUserRequest createUserRequest) {
		try{
			AppUser appUser = new AppUser();
			appUser.setUsername(createUserRequest.getUsername());
			Cart cart = new Cart();
			cartRepository.save(cart);
			appUser.setCart(cart);

			if(createUserRequest.getPassword().length() < 7 ||
					!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
				throw new ApiException(ExceptionTypes.CREATEUSER, createUserRequest.getUsername());
			}

			appUser.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
			userRepository.save(appUser);
			log.info("UserCreation = success UserName =" + createUserRequest.getUsername());
			System.err.println(appUser);
			return ResponseEntity.ok(appUser);


		}catch(ApiException a){
			return ResponseEntity.badRequest().build();
		}
	}
}
