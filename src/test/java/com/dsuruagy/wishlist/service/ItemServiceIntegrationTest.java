package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.ItemRepository;
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
import java.math.BigDecimal;
import java.util.Random;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-datasets.xml")
@Transactional
public class ItemServiceIntegrationTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private WishListRepository wishListRepository;

    private ItemService itemService;
    private WishListService wishListService;

    @BeforeEach
    public void setUp() {
        itemService = new ItemService(itemRepository, wishListRepository);
        wishListService = new WishListService(wishListRepository);
    }

    @Test
    public void createNewItemTest() {
        WishList wishList = wishListService.findByIdWithAllItems(1L);
        Assertions.assertNotNull(wishList);

        Item item = new Item();
        item.setName("Fone de Ouvido, JBL, Tune 110 Intra-Auricular, Preto");
        item.setUrl("https://www.amazon.com.br/dp/B01MG62Z5M/?coliid=I16KA24S90HERU&colid=17ARWAJJQ7ZEL&psc=1&ref_=lv_ov_lig_dp_it");
        item.setCurrentPrice(new BigDecimal("13.89"));
        item.addWishList(wishList);

        Item createdItem = Assertions.assertDoesNotThrow(() -> itemService.create(item));
        Assertions.assertNotNull(createdItem.getDateCreated());
        Assertions.assertFalse(createdItem.getWishLists().isEmpty());
    }

    public Item createNewItem(Long wishListId) {
        WishList wishList = wishListService.findByIdWithAllItems(wishListId);

        Item item = new Item();
        item.setName("Simple item " + new Random().nextInt(100));
        item.addWishList(wishList);

        wishListService.save(wishList);
        return item;
    }

    @Test
    public void deleteItemTest() {
        Long wishListId = 1L;

        Item firstItem = createNewItem(wishListId);

        int sizeBefore = wishListService.findByIdWithAllItems(wishListId).getItems().size();
        System.out.println("sizeBefore: " + sizeBefore);
        Assertions.assertEquals(firstItem,
                wishListService.findByIdWithAllItems(wishListId).getItems().stream().findFirst().get());

        Item secondItem = createNewItem(wishListId);
        Assertions.assertEquals(sizeBefore + 1,
                wishListService.findByIdWithAllItems(wishListId).getItems().size());

        itemService.delete(secondItem);

        Assertions.assertEquals(sizeBefore,
                wishListService.findByIdWithAllItems(wishListId).getItems().size());

    }
}
