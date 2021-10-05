package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
