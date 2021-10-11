package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.UserRepository;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListServiceIntegrationTest {
    private WishListService wishListService;
    private UserService userService;
    
    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        wishListService = new WishListService(wishListRepository);
        userService = new UserService(userRepository);
    }

    @Test
    public void createWishListTest() {
        WishList wishList = new WishList();
        wishList.setName("My wish list");
        wishList.setDescription("Creating new WishList");
        wishList.setPublicToOthers(Boolean.TRUE);
        wishList.setType('P');
        wishList.setOpened(Boolean.TRUE);

        wishList.setOwner(userService.getById(1L));

        Assertions.assertNotNull(wishListService.create(wishList).getDateCreated());
    }

    @Test
    public void findAllByUserIdTest() {
        Assertions.assertNotNull(wishListService.findAllByOwner(1L));
    }
}
