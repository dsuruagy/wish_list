package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.business.exception.BusinessException;
import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.User;
import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.ItemRepository;
import com.dsuruagy.wishlist.repository.UserRepository;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceIntegrationTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private ItemService itemService;
    private WishListService wishListService;

    @BeforeEach
    public void setUp() {
        itemService = new ItemService(itemRepository);
        userService = new UserService(userRepository);
        wishListService = new WishListService(wishListRepository);
    }

    @Test
    @Order(1)
    public void createUserTest() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("userpass");
        user.setEmail("test@dsuruagy.com");
        user.setFirstName("Testing");
        user.setLastName("User");

        Assertions.assertNotNull(userService.create(user));
    }

    @Test
    @Order(2)
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
    public void createNewItemWithoutWishListTest() {
        Item item = new Item();
        item.setName("A new Item");
        item.setUrl("http://www.amazon.com.br");
        item.setCurrentPrice(new BigDecimal("13.89"));

        Exception exception = Assertions.assertThrows(BusinessException.class, () -> {
           itemService.create(item);
        });

        String message = "be associated with at least one wishlist";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(message));
    }

    @Test
    public void createNewItemTest() {
        WishList wishList = wishListService.findByIdWithAllItems(1L);
        Assertions.assertNotNull(wishList);

        Item item = new Item();
        item.setName("A new Item");
        item.setUrl("http://www.amazon.com.br");
        item.setCurrentPrice(new BigDecimal("13.89"));
        item.addWishList(wishList);

        Item createdItem = Assertions.assertDoesNotThrow(() -> itemService.create(item));
        Assertions.assertNotNull(createdItem.getDateCreated());
        Assertions.assertFalse(createdItem.getWishLists().isEmpty());
    }
}
