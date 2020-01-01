package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void initTest(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItemById() throws Exception{
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        //assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetItemByName() throws Exception{
        ResponseEntity<Item> response = itemController.getItemByName("name");
        assertNotNull(response);
       // assertEquals(200, response.getStatusCodeValue());
    }
}
