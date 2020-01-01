package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private ModifyCartRequest modifyCartRequest =mock(ModifyCartRequest.class);

    @Before
    public void initTest(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        Optional<AppUser> optionalAppUser= Optional.of(TestUtils.getAppUser());
        Optional<Item> optionalItem= Optional.of(TestUtils.getItem());
        when(userRepository.findById(any())).thenReturn(optionalAppUser);
        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(itemRepository.findById(any())).thenReturn(optionalItem);
        when(itemRepository.findByName(any())).thenReturn(optionalItem);
    }

    @Test
    public void testAddToCart() throws Exception{
        ResponseEntity<Cart> response = cartController.addToCart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testRemoveFromCart() throws Exception{
        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
