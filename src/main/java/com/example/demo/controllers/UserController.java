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
			System.out.println("UserIDSearch = " + id);
			Optional<AppUser> optionalAppUser = userRepository.findById(id);
			if (optionalAppUser == null){
				throw new ApiException(ExceptionTypes.SEARCHUSER, id.toString());
			}else if(!optionalAppUser.isPresent()){
				throw new ApiException(ExceptionTypes.SEARCHUSER, id.toString());
			}else{
				log.info("UserIdFound =  " + id);
				return ResponseEntity.ok(optionalAppUser.get());
			}
		}catch(ApiException a){
				return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<AppUser> findByUserName(@PathVariable String username) {
		try{
			log.info("UserNameSearch =  " + username);
			System.out.println("UserNameSearch =  " + username);
			Optional<AppUser> appUser = userRepository.findByUsername(username);
			if(appUser == null){
				throw new ApiException(ExceptionTypes.SEARCHUSER, username);
			}else if(!appUser.isPresent()){
				throw new ApiException(ExceptionTypes.SEARCHUSER, username);
			}else{
				log.info("UserNameFound =  " + username);
				return ResponseEntity.ok(appUser.get());
			}
		}catch(ApiException a){
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<AppUser> createUser(@RequestBody CreateUserRequest createUserRequest) {
		try{
			AppUser newUser = new AppUser();
			newUser.setUsername(createUserRequest.getUsername());

			if(createUserRequest==null){
				throw new ApiException(ExceptionTypes.CREATEUSER, null);
			}else if(newUser.getUsername() == null){
				throw new ApiException(ExceptionTypes.CREATEUSER, createUserRequest.getUsername());
			}else if(createUserRequest.getPassword().length() < 7){
				throw new ApiException(ExceptionTypes.CREATEUSER, createUserRequest.getUsername());
			}else if(!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
				throw new ApiException(ExceptionTypes.CREATEUSER, createUserRequest.getUsername());
			}else{
				Cart cart = new Cart();
				cartRepository.save(cart);
				newUser.setCart(cart);

				newUser.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
				userRepository.save(newUser);
				log.info("UserCreation = success UserName =" + createUserRequest.getUsername());
				return ResponseEntity.ok(newUser);
			}
		}catch(ApiException a){
			return ResponseEntity.badRequest().build();
		}
	}
}
