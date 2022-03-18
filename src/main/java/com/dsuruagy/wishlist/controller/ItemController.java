package com.dsuruagy.wishlist.controller;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.exception.BusinessException;
import com.dsuruagy.wishlist.exception.ConflictException;
import com.dsuruagy.wishlist.exception.NotFoundException;
import com.dsuruagy.wishlist.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody Item item) throws ConflictException {
        return itemService.create(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Item findById(@PathVariable(value = "id") long id) throws NotFoundException {
        return itemService.findById(id);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public void handleException(ConflictException ex) {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleException(NotFoundException ex) {
    }
}

