package com.mjt.tu.alumni.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(12) default 'USER'")
    private UserType type;

    @OneToMany(mappedBy = "user")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "owner")
    private Set<Album> albums;

    @OneToMany(mappedBy = "owner")
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "sender")
    private Set<PhotoSession> sentSessionRequests;

    @OneToMany(mappedBy = "receiver")
    private Set<PhotoSession> receivedSessionRequests;

    @OneToMany(mappedBy = "sender")
    private Set<ItemRequest> sentItemRequests;

    @OneToMany(mappedBy = "receiver")
    private Set<ItemRequest> receivedItemRequests;

    @OneToMany(mappedBy = "sender")
    private Set<JoinRequest> sentJoinRequests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @ManyToMany
    @JoinTable(name = "membership", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Group> groups;

    public Set<PhotoSession> getSentSessionRequests() {
        return sentSessionRequests;
    }

    public void setSentSessionRequests(Set<PhotoSession> sentSessionRequests) {
        this.sentSessionRequests = sentSessionRequests;
    }

    public Set<PhotoSession> getReceivedSessionRequests() {
        return receivedSessionRequests;
    }

    public void setReceivedSessionRequests(Set<PhotoSession> receivedSessionRequests) {
        this.receivedSessionRequests = receivedSessionRequests;
    }

    public Set<ItemRequest> getSentItemRequests() {
        return sentItemRequests;
    }

    public void setSentItemRequests(Set<ItemRequest> sentItemRequests) {
        this.sentItemRequests = sentItemRequests;
    }

    public Set<ItemRequest> getReceivedItemRequests() {
        return receivedItemRequests;
    }

    public void setReceivedItemRequests(Set<ItemRequest> receivedItemRequests) {
        this.receivedItemRequests = receivedItemRequests;
    }

    public Set<JoinRequest> getSentJoinRequests() {
        return sentJoinRequests;
    }

    public void setSentJoinRequests(Set<JoinRequest> sentJoinRequests) {
        this.sentJoinRequests = sentJoinRequests;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", password=" + password + ", name=" + name + ", type=" + type + ", photos=" + photos
                + "]";
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    public void addCartItem(CartItem item) {
        this.cartItems.add(item);
    }

    public void addAlbum(Album item) {
        this.albums.add(item);
    }

    public void addJoinRequest(JoinRequest request) {
        this.sentJoinRequests.add(request);
    }

    public void addSentItemRequest(ItemRequest request) {
        this.sentItemRequests.add(request);
    }

    public void addReceivedItemRequest(ItemRequest request) {
        this.receivedItemRequests.add(request);
    }

    public void addSentSessionRequest(PhotoSession request) {
        this.sentSessionRequests.add(request);
    }

    public void addReceivedSessionRequest(PhotoSession request) {
        this.receivedSessionRequests.add(request);
    }

    public User withoutPassword() {
        User result = new User();
        result.setName(this.name);
        return result;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.name()));
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
