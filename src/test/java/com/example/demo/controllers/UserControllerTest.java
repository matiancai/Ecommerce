package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration*/
public class UserControllerTest {

    private UserController userController;

    @Autowired private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void initTest(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void testCreateUser() throws Exception{

        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("hashedPassword");

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testUser");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");

        ResponseEntity<AppUser> response = userController.createUser(createUserRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        AppUser createdUser = response.getBody();
        assertNotNull(createdUser);
        assertEquals(0, createdUser.getId());
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("hashedPassword", createdUser.getPassword());

       /* ResponseEntity<AppUser> findResponse = userController.findById((long) 0);
        assertNotNull(findResponse);
        assertEquals(200, findResponse.getStatusCodeValue());*/
        /*List<AppUser> appUserList = userRepository.findAll();
        System.out.println(appUserList);*/
    }

   /* @Test
    public void testGetUserById() throws Exception{
        ResponseEntity<AppUser> response = userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetUserByName() throws Exception{
        ResponseEntity<AppUser> response = userController.findByUserName("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }*/
}
