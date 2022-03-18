package com.dsuruagy.wishlist.controller;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.WishList;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.math.BigDecimal;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishListControllerTest {
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public static final String URL = "/wishLists";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DatabaseSetup("classpath:test-datasets.xml")
    @DatabaseTearDown
    @Order(value=1)
    // TODO: Try to identify why this test fails if it is not the first one.
    public void findOneWishListTest() {
        ResponseEntity<WishList> response =
                restTemplate.getForEntity(URL + "/1", WishList.class);

        LOGGER.debug("response.getBody():" + response.getBody());
        assertThat(response.getStatusCode(),
                is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getName(), is("Wishlist test 1"));

    }

    @Test
    public void findAllWishListsTest() {
        ResponseEntity<WishList[]> response =
            restTemplate.getForEntity(URL, WishList[].class);

        assertThat(response.getStatusCode(),
                is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).length, is(2));
        //https://stackoverflow.com/questions/41567455/spring-boots-testresttemplate-with-hateoas-pagedresources
    }

    @Test
    @DatabaseSetup("classpath:test-datasets.xml")
    @DatabaseTearDown
    public void postNewItemTest() {
        Item item = new Item();
        item.setName("New Item");
        item.setUrl(URL);
        item.setCurrentPrice(new BigDecimal("12.00"));

        ResponseEntity<Item> response = restTemplate.postForEntity(URL + "/1/items", item, Item.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    }
}
