package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.business.exception.BusinessException;
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

import java.math.BigDecimal;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-datasets.xml")
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

        Exception exception = Assertions.assertThrows(BusinessException.class, () ->
           itemService.create(item)
        );

        String message = "be associated with at least one wishlist";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(message));
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
}
