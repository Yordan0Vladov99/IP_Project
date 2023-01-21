package com.mjt.tu.alumni.models;

import jakarta.persistence.*;

@Entity
@Table(name = "item_requests")
public class ItemRequest extends Request {
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    public ItemRequest() {
        super();
    }

    public ItemRequest(String description, Status status, User sender, User receiver, Photo photo) {
        super(description, status, sender);
        this.receiver = receiver;
        this.photo = photo;
    }

}
