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

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private AppUser appUser = mock(AppUser.class);
    private Optional<AppUser> optionalAppUser;


    @Before
    public void initTest(){
        userController = new UserController();
        optionalAppUser= Optional.of(TestUtils.getAppUser());
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void testFindUserByIdSuccess(){
        when(userRepository.findById(any())).thenReturn(optionalAppUser);
        ResponseEntity<AppUser> response = userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindUserByIdFailure(){
        when(userRepository.findById(any())).thenReturn(null);
        ResponseEntity<AppUser> response = userController.findById(0L);
        System.out.println(response);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testFindUserByUsernameSuccess(){
        when(userRepository.findByUsername(any())).thenReturn(optionalAppUser);
        ResponseEntity<AppUser> response = userController.findByUserName("testUser");
        System.out.println(response);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindUserByUsernameFailure(){
        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<AppUser> response = userController.findByUserName("testUser");
        System.out.println(response);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUserSuccess() throws Exception{
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
    public void testCreateUserFailure() throws Exception{

        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("hashedPassword");

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(null);
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");

        ResponseEntity<AppUser> response = userController.createUser(createUserRequest);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        CreateUserRequest createUserRequest2 = new CreateUserRequest();
        createUserRequest2.setUsername("testUser");
        createUserRequest2.setPassword("testPassword");
        createUserRequest2.setConfirmPassword("testPass");

        ResponseEntity<AppUser> response2 = userController.createUser(createUserRequest2);

        assertNotNull(response2);
        assertEquals(400, response2.getStatusCodeValue());
    }
}
