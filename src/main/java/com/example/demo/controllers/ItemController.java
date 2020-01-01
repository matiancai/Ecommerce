package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		Optional<Item> item = itemRepository.findById(id);
		if(!item.isPresent()){
			return ResponseEntity.notFound().build();
		}else{
			return ResponseEntity.of(item);
		}
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Item> getItemByName(@PathVariable String name) {
		Optional<Item> item = itemRepository.findByName(name);
		if(!item.isPresent()){
			return ResponseEntity.notFound().build();
		}else{
			return ResponseEntity.of(item);
		}
	}
}
