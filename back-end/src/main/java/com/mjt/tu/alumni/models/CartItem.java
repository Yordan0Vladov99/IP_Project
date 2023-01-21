package com.mjt.tu.alumni.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem extends Item {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Photo image;

    public CartItem() {
        super();
    }

    public CartItem(int price, String type, User owner) {
        super(price, owner);
        this.type = type;
    }

    public Photo getImage() {
        return image;
    }

    public void setImage(Photo image) {
        this.image = image;
    }

}
