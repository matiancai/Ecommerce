package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
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

public class OrderControllerTest {

    private OrderController orderController;

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void initTest(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);

        Optional<AppUser> optionalAppUser= Optional.of(TestUtils.getAppUser());
        Optional<UserOrder> optionalOrder= Optional.of(TestUtils.getOrder());
        Optional<Cart> optionalCart= Optional.of(TestUtils.getCart());

        List<Item> itemList = new ArrayList<Item>() {};
        List<UserOrder> orderList = new ArrayList<UserOrder>() {};

        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        //when(optionalAppUser.get().getCart()).thenReturn(optionalCart.get());
        when(optionalOrder.get().createFromCart(any())).thenReturn(optionalOrder.get());

        when(userRepository.findById(any())).thenReturn(optionalAppUser);
        //when(UserOrder.createFromCart(any())).thenReturn(optionalOrder);
        when(orderRepository.findByAppUser(any())).thenReturn(orderList);
        when(optionalCart.get().getItems().size()).thenReturn(1);
    }

    @Test
    public void testSubmitOrder(){
        ResponseEntity<UserOrder> response = orderController.submit("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser(){
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
