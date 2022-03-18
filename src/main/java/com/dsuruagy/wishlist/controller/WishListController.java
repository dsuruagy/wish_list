package com.dsuruagy.wishlist.controller;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/wishLists")
public class WishListController {
    @Autowired
    public WishListService wishListService;

    @PostMapping(path = "{id}/items")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void postItem(@PathVariable(value = "id") long id,
                              @RequestBody Item item) {
        WishList wishList = wishListService.findByIdWithAllItems(id);
        item.addWishList(wishList);

        wishListService.save(wishList);
    }

    @GetMapping
    public List<WishList> findAll() {
        return wishListService.findAll();
    }
}
