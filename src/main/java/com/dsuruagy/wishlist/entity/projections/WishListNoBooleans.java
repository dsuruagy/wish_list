package com.dsuruagy.wishlist.entity.projections;

import com.dsuruagy.wishlist.entity.Item;
import com.dsuruagy.wishlist.entity.User;
import com.dsuruagy.wishlist.entity.WishList;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.Set;

@Projection(name = "noBooleans", types = {WishList.class})
public interface WishListNoBooleans {
    String getName();
    String getDescription();
    LocalDate getDateCreated();
    LocalDate getDateModified();
    Character getType();
    User getOwner();
    Set<Item> getItems();
}
