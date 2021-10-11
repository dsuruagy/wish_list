package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByOwnerId(Long id);
}
