package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByOwnerId(Long id);

    @Query("SELECT w FROM WishList w LEFT JOIN FETCH w.items WHERE w.id = ?1")
    WishList findByIdWithAllItems(Long id);

    WishList findByName(String name);
}
