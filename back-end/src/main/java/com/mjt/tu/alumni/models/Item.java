package com.mjt.tu.alumni.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@MappedSuperclass
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Item() {
    }

    public Item(int price, User owner) {
        this.price = price;
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
