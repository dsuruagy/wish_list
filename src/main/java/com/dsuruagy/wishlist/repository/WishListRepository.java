package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
