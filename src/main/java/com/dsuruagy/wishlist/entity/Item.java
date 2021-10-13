package com.dsuruagy.wishlist.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ITEM")
public class Item {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column(name = "DATE_CREATED")
    private LocalDate dateCreated;

    @Column(name = "CURRENT_PRICE")
    private BigDecimal currentPrice;

    @ManyToMany
    @JoinTable(name="WISH_LIST_ITEM",
    joinColumns = @JoinColumn(name = "ITEM_ID"),
    inverseJoinColumns = @JoinColumn(name = "WISH_LIST_ID"))
    private Set<WishList> wishLists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Set<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(Set<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    public void addWishList(WishList wishList) {
        this.getWishLists().add(wishList);
        wishList.getItems().add(this);
    }

    public void removeWishList(WishList wishList) {
        this.getWishLists().remove(wishList);
        wishList.removeItem(this);
    }
}
