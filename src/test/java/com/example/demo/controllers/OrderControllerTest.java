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

    private UserOrder userOrder = mock(UserOrder.class);

    Optional<AppUser> optionalAppUser;
    Optional<UserOrder> optionalOrder;
    Optional<Cart> optionalCart;

    @Before
    public void initTest(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController,"userOrder", userOrder);
        optionalAppUser= Optional.of(TestUtils.getAppUser());
        optionalOrder= Optional.of(TestUtils.getOrder());
        optionalCart= Optional.of(TestUtils.getCart());
    }

    @Test
    public void testSubmitOrderSuccess(){
        List<Item> itemList = new ArrayList<Item>() {};
        List<UserOrder> orderList = new ArrayList<UserOrder>() {};

        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(orderRepository.findByAppUser(any())).thenReturn(orderList);
        ResponseEntity<UserOrder> response = orderController.submit("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testSubmitOrderFailure(){
        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit("testUser");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUserSuccess(){
        List<Item> itemList = new ArrayList<Item>() {};
        List<UserOrder> orderList = new ArrayList<UserOrder>() {};

        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(orderRepository.findByAppUser(any())).thenReturn(orderList);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUserFailure(){
        List<Item> itemList = new ArrayList<Item>() {};
        List<UserOrder> orderList = new ArrayList<UserOrder>() {};

        //Testing no user found - return null for user search
        when(userRepository.findByUsername(any())).thenReturn(null);
        when(orderRepository.findByAppUser(any())).thenReturn(orderList);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUser");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        //Testing no orders found - return null list of orders for user
        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        when(orderRepository.findByAppUser(any())).thenReturn(null);

        ResponseEntity<List<UserOrder>> response2 = orderController.getOrdersForUser("testUser");
        assertNotNull(response2);
        assertEquals(404, response2.getStatusCodeValue());
    }


}
