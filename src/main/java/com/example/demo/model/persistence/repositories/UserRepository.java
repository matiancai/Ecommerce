package com.example.demo.model.persistence.repositories;

import com.example.demo.model.persistence.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByUsername(String username);
	public Optional<AppUser> findById(Long id);
}
