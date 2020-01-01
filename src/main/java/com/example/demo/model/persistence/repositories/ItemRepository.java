package com.example.demo.model.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	Optional<Item> findByName(String name);
	Optional<Item> findById(Long id);

}
