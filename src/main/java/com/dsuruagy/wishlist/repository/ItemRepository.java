package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.wishLists WHERE i.name = ?1")
    Item findByNameWithAllWishLists(String name);
}
