package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.exception.ConflictException;
import com.dsuruagy.wishlist.exception.NotFoundException;
import com.dsuruagy.wishlist.repository.ItemRepository;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final WishListRepository wishListRepository;

    public ItemService(ItemRepository itemRepository, WishListRepository wishListRepository) {
        this.itemRepository = itemRepository;
        this.wishListRepository = wishListRepository;
    }

    public Item create(Item item) throws ConflictException {
        Item saved;
        try {
            saved = itemRepository.save(item);
        } catch (DataIntegrityViolationException dive) {
            throw new ConflictException("You cannot insert a duplicate item.");
        }
        return saved;
    }

    public Item findById(Long id) throws NotFoundException {
        return itemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void delete(Item item) {
        Item i = itemRepository.findByNameWithAllWishLists(item.getName());
        i.getWishLists().forEach(w -> {
            WishList wlWithItems = wishListRepository.findByIdWithAllItems(w.getId());
            wlWithItems.removeItem(i);
            wishListRepository.save(wlWithItems);
        });

        itemRepository.delete(i);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
