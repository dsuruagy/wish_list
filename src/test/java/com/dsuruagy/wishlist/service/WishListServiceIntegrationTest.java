package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.UserRepository;
import com.dsuruagy.wishlist.repository.WishListRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-datasets.xml")
@Transactional
public class WishListServiceIntegrationTest {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private WishListService wishListService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
        wishListService = new WishListService(wishListRepository);
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

    @Test
    public void deleteWishList() {
        createWishListTest();
        Assertions.assertTrue(wishListService.findAllByOwner(1L).size() > 1);

        Long id = wishListService.findByName("My wish list").getId();
        wishListService.delete(id);

        Assertions.assertEquals(2, wishListService.findAllByOwner(1L).size());

    }
}
