package com.dsuruagy.wishlist.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="ITEM",
        uniqueConstraints = @UniqueConstraint(name = "UN_ITEM", columnNames = {"name"}))
public class Item {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,
            columnDefinition = "varchar(100)")
    private String name;

    @Column(columnDefinition = "varchar(300)")
    private String url;

    @Column(name = "DATE_CREATED", nullable = false, updatable = false)
    private LocalDate dateCreated = LocalDate.now();

    @Column(name = "CURRENT_PRICE")
    private BigDecimal currentPrice;

    @ManyToMany(mappedBy = "items")
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

    public void addWishList(WishList wishList) {
        this.getWishLists().add(wishList);
        wishList.getItems().add(this);
    }

    public void removeWishList(WishList wishList) {
        this.getWishLists().remove(wishList);
        wishList.removeItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) && Objects.equals(url, item.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
