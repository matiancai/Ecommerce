package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private Optional<Item> optionalItem;
    private List<Item> itemList;

    @Before
    public void initTest(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        optionalItem= Optional.of(TestUtils.getItem());
        itemList = new ArrayList<Item>(){};
    }

    @Test
    public void testGetItemByIdSuccess() throws Exception{
        when(itemRepository.findById(any())).thenReturn(optionalItem);
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetItemByIdFailure() throws Exception{
        when(itemRepository.findById(any())).thenReturn(null);
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetItemByNameSuccess() throws Exception{
        when(itemRepository.findByName(any())).thenReturn(optionalItem);
        ResponseEntity<Item> response = itemController.getItemByName("name");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetItemByNameFailure() throws Exception{
        when(itemRepository.findByName(any())).thenReturn(null);
        ResponseEntity<Item> response = itemController.getItemByName("name");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testFindAllSuccess() throws Exception{
        when(itemRepository.findAll()).thenReturn(itemList);
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindAllFailure() throws Exception{
        when(itemRepository.findAll()).thenReturn(null);
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
