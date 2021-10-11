package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.User;
import com.dsuruagy.wishlist.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceIntegrationTest {
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void createUserTest() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("userpass");
        user.setEmail("test@dsuruagy.com");
        user.setFirstName("Testing");
        user.setLastName("User");

        Assertions.assertNotNull(userService.create(user));
    }
}
