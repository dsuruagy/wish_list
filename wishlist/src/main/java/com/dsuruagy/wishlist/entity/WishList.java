package com.dsuruagy.wishlist.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class WishList {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Character isPublic;

    @Column
    private LocalDate dateCreated;

    @Column
    private LocalDate dateModified;

    @ManyToOne
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

    public Character getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Character isPublic) {
        this.isPublic = isPublic;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
