package com.dsuruagy.wishlist.controller;

import com.dsuruagy.wishlist.entity.Item;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
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
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-datasets.xml")
public class ItemControllerTest {
    public static final String NAME = "Controller Integration Item Test";
    public static final String URL = "https://example.com";
    public static final String VAL = "399.99";
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public static final String REST_URL = "/items";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveItem() {
        Item item = new Item();
        item.setName(NAME + new Random().nextInt(1000));
        item.setUrl(URL);
        item.setCurrentPrice(new BigDecimal(VAL));

        ResponseEntity response = restTemplate.postForEntity(REST_URL, item, Item.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void findOneItemTest() {
        saveItem();

        ResponseEntity<Item> response =
                restTemplate.getForEntity(REST_URL + "/1", Item.class);

        assertThat(response.getStatusCode(),
                is(HttpStatus.OK));
        assertThat(response.getBody().getUrl(), is(URL));

    }

}
