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
		try{
			List<Item> itemList = itemRepository.findAll();
			if(itemList==null){
				throw new ApiException(ExceptionTypes.SEARCHITEM, "All Items");
			}else{
				return ResponseEntity.ok(itemList);
			}
		}catch(ApiException e){
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		try{
			Optional<Item> item = itemRepository.findById(id);
			if(item==null){
				throw new ApiException(ExceptionTypes.SEARCHITEM, id.toString());
			}else if(!item.isPresent()){
				throw new ApiException(ExceptionTypes.SEARCHITEM, id.toString());
			}else{
				return ResponseEntity.of(item);
			}
		}catch(ApiException e){
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Item> getItemByName(@PathVariable String name) {
		try{
			Optional<Item> item = itemRepository.findByName(name);
			if(item == null){
				throw new ApiException(ExceptionTypes.SEARCHITEM, name);
			}else if(!item.isPresent()){
				throw new ApiException(ExceptionTypes.SEARCHITEM, name);
			}else{
				return ResponseEntity.of(item);
			}
		}catch(ApiException e){
			return ResponseEntity.notFound().build();
		}
	}
}
