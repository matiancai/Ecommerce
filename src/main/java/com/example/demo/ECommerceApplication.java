package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.UserOrder;
import org.apache.log4j.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaRepositories("com.example.demo.model.persistence.repositories")
@EntityScan(basePackages = {"com.example.demo.model","com.example.demo.model.persistence"})
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class ECommerceApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserOrder userOrder(){return new UserOrder();}

	public static void main(String[] args) {
		System.out.println("Starting application");
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
