package com.dsuruagy.wishlist.entity;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "WISH_LIST")
public class WishList {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    @Column(name = "PUBLIC")
    private Boolean publicToOthers;

    @Column(name = "DATE_CREATED")
    private LocalDate dateCreated;

    @Column(name = "DATE_MODIFIED")
    private LocalDate dateModified;

    /**
     * Allowed values are T - temporary, like supermarket, or P - persistent
     */
    @Column
    private Character type;

    @Column
    private Boolean opened;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @ManyToMany(mappedBy = "wishLists")
    private Set<Item> items;

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
}
