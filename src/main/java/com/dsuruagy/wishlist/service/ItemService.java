package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.WishList;
import com.dsuruagy.wishlist.repository.ItemRepository;
import com.dsuruagy.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final WishListRepository wishListRepository;

    public ItemService(ItemRepository itemRepository, WishListRepository wishListRepository) {
        this.itemRepository = itemRepository;
        this.wishListRepository = wishListRepository;
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item getById(Long id) {
        return itemRepository.getById(id);
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
}
