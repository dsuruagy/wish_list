package com.dsuruagy.wishlist.controller;

import com.dsuruagy.wishlist.entity.Item;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.*;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-datasets.xml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemControllerTest {
    public static final String NAME = "Controller Integration Item Test";
    public static final String URL = "https://example.com";
    public static final String VAL = "399.99";
    public static final String REST_URL = "/items";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveItem() {
        Item item = new Item();
        item.setName(NAME);
        item.setUrl(URL);
        item.setCurrentPrice(new BigDecimal(VAL));

        // Many to many RESTful API
        // https://stackoverflow.com/questions/6324547/how-can-i-handle-many-to-many-relationships-in-a-restful-api

        ResponseEntity<Item> response = restTemplate.postForEntity(REST_URL, item, Item.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void nameConflictTest() {
        Item item = new Item();
        item.setName(NAME + " Another One");
        item.setUrl(URL + "/another");
        item.setCurrentPrice(new BigDecimal("200"));

        ResponseEntity<Item> response = restTemplate.postForEntity(REST_URL, item, Item.class); // First insert
        response = restTemplate.postForEntity(REST_URL, item, Item.class); // Second insert

        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
    }

    @Test
    @Order(value = 1)
    // TODO: Try to identify why this test must be the first one. It will not work unless this annotation is been used.
    public void findOneItemTest() {
        Item item = new Item();
        item.setName(NAME + " More than one");
        item.setUrl(URL + "/another");
        item.setCurrentPrice(new BigDecimal("200"));

        ResponseEntity<Item> responsePost = restTemplate.postForEntity(REST_URL, item, Item.class); // First insert

        ResponseEntity<Item> responseGet =
                restTemplate.getForEntity(REST_URL + "/2", Item.class);

        assertThat(responseGet.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseGet.getBody(), notNullValue());
        assertThat(Objects.requireNonNull(responseGet.getBody()).getUrl(), is(URL + "/another"));

    }

}
