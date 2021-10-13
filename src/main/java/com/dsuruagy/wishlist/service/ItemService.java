package com.dsuruagy.wishlist.service;

import com.dsuruagy.wishlist.business.exception.BusinessException;
import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(Item item) throws BusinessException {
        item.setDateCreated(LocalDate.now());

        return this.save(item);
    }

    public Item getById(Long id) {
        return itemRepository.getById(id);
    }

    public Item save(Item item) throws BusinessException {
        if(item.getWishLists().isEmpty()) {
            throw new BusinessException("Need to be associated with at least one wishlist");
        }
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
