package com.dsuruagy.wishlist.controller;

import com.dsuruagy.wishlist.entity.WishList;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Disabled;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-datasets.xml")
public class WishListControllerTest {
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public static final String URL = "/wishLists";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findOneWishListTest() {
        ResponseEntity<WishList> response =
                restTemplate.getForEntity(URL + "/1", WishList.class);

        LOGGER.debug("response.getBody():" + response.getBody());
        assertThat(response.getStatusCode(),
                is(HttpStatus.OK));
        assertThat(response.getBody().getName(), is("Wishlist test"));

    }

    @Test
    @Disabled
    public void findAllWishListsTest() {
        ResponseEntity<WishList[]> response =
            restTemplate.getForEntity(URL, WishList[].class);

        assertThat(response.getStatusCode(),
                is(HttpStatus.OK));
        assertThat(response.getBody().length, is(3));
        //https://stackoverflow.com/questions/41567455/spring-boots-testresttemplate-with-hateoas-pagedresources
    }
}
