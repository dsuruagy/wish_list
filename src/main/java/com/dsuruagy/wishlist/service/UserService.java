package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.User;
import com.dsuruagy.wishlist.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(@Valid User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }
}
