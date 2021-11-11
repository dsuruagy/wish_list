package com.dsuruagy.wishlist.entity;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "WISH_LIST")
public class WishList {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @NotNull
    private String name;

    @Column(columnDefinition = "varchar(4000)")
    private String description;

    @Column(name = "PUBLIC_TO_OTHERS", columnDefinition = "tinyint default 1")
    private Boolean publicToOthers;

    @Column(name = "DATE_CREATED", nullable = false)
    private LocalDate dateCreated = LocalDate.now();

    @Column(name = "DATE_MODIFIED")
    private LocalDate dateModified;

    /**
     * Allowed values are T - temporary, like supermarket, or P - persistent
     */
    @Column(nullable = false, columnDefinition = "char(1) default 'P'")
    private Character type;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean opened;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="WISH_LIST_ITEM",
            joinColumns = @JoinColumn(name = "WISH_LIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private Set<Item> items = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isPublicToOthers() {
        return publicToOthers;
    }

    public void setPublicToOthers(Boolean publicToOthers) {
        this.publicToOthers = publicToOthers;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public Boolean isOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.getItems().add(item);
        item.getWishLists().add(this);
    }

    public void removeItem(Item item) {
        this.getItems().remove(item);
        item.getWishLists().remove(this);
    }
}
