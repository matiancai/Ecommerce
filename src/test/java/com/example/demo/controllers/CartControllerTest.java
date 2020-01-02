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
    private Optional<AppUser> optionalAppUser;
    private Optional<Item> optionalItem;

    @Before
    public void initTest(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        optionalAppUser = Optional.of(TestUtils.getAppUser());
        optionalItem = Optional.of(TestUtils.getItem());
    }

    @Test
    public void testAddToCartSuccess() throws Exception{
        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(itemRepository.findById(any())).thenReturn(optionalItem);
        ResponseEntity<Cart> response = cartController.addToCart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testAddToCartFailure() throws Exception{
        when(userRepository.findByUsername(any())).thenReturn(null);//Test user not found
        when(itemRepository.findById(any())).thenReturn(optionalItem);

        ResponseEntity<Cart> response = cartController.addToCart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(itemRepository.findById(any())).thenReturn(null);//Test item not found
        ResponseEntity<Cart> response2 = cartController.addToCart(modifyCartRequest);
        assertNotNull(response2);
        assertEquals(404, response2.getStatusCodeValue());
    }

    @Test
    public void testRemoveFromCartSuccess() throws Exception{
        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(itemRepository.findById(any())).thenReturn(optionalItem);
        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testRemoveFromCartFailure() throws Exception{

        when(userRepository.findByUsername(any())).thenReturn(null); //Test user not found
        when(itemRepository.findById(any())).thenReturn(optionalItem);

        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(itemRepository.findById(any())).thenReturn(null);//Test item not found

        ResponseEntity<Cart> response2 = cartController.removeFromCart(modifyCartRequest);
        assertNotNull(response2);
        assertEquals(404, response2.getStatusCodeValue());
    }
}
