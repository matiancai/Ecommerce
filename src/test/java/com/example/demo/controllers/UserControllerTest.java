package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

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
    }

    @Test
    public void testGetUserById() throws Exception{
        ResponseEntity<AppUser> response = userController.findById(1L);
        assertNotNull(response);
    }


}
