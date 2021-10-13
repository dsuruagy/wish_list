package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.business.exception.BusinessException;
import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.ItemRepository;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class ItemServiceIntegrationTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private WishListRepository wishListRepository;

    private ItemService itemService;
    private WishListService wishListService;

    @BeforeEach
    public void setUp() {
        itemService = new ItemService(itemRepository);
        wishListService = new WishListService(wishListRepository);
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
