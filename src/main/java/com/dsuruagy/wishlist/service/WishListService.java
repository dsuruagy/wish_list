package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class WishListService {
    private final WishListRepository repository;

    public WishListService(WishListRepository repository) {
        this.repository = repository;
    }

    /**
     * Persist a new Wishlist.
     * @param wishList New wishlist to be created.
     * @return The wishlist created.
     */
    public WishList create(@Valid WishList wishList) {
        wishList.setDateCreated(LocalDate.now());

        return repository.save(wishList);
    }

    /**
     * Find all wishlists of a user.
     * @param id Owner's Id.
     * @return List of all owner's wishlists.
     */
    public List<WishList> findAllByOwner(Long id) {
        return repository.findAllByOwnerId(id);
    }

    public WishList findByIdWithAllItems(Long id) {
        return repository.findByIdWithAllItems(id);
    }

    public WishList findByName(String name) {
        return repository.findByName(name);
    }

    public WishList getById(Long id) {
        return repository.getById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
