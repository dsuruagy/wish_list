package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test created following this tutorial: https://reflectoring.io/unit-testing-spring-boot/
 */
public class WishListServiceUnitTest {
    private WishListService service;
    private final WishListRepository repository = Mockito.mock(WishListRepository.class);

    @BeforeEach
    public void setup() {
        service = new WishListService(repository);
    }

    @Test
    public void createWishListTest() {
        WishList wishList = new WishList();
        wishList.setName("My wish list");
        wishList.setDescription("Creating new WishList");
        wishList.setPublicToOthers(Boolean.TRUE);
        wishList.setType('P');
        wishList.setOpened(Boolean.TRUE);

        when(repository.save(any(WishList.class))).then(returnsFirstArg());
        Assertions.assertNotNull(service.create(wishList).getDateCreated());
    }
}
