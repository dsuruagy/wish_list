package com.dsuruagy.wishlist.repository;

import com.dsuruagy.wishlist.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Item> entities);

    @Override
    @RestResource(exported = false)
    <S extends Item> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    <S extends Item> S save(S entity);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Item entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.wishLists WHERE i.name = ?1")
    Item findByNameWithAllWishLists(String name);


}
