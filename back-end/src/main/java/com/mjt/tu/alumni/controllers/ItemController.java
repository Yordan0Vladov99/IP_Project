package com.mjt.tu.alumni.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mjt.tu.alumni.dtos.CartItemDto;
import com.mjt.tu.alumni.models.Album;
import com.mjt.tu.alumni.models.CartItem;
import com.mjt.tu.alumni.models.Photo;
import com.mjt.tu.alumni.models.User;
import com.mjt.tu.alumni.repos.AlbumRepository;
import com.mjt.tu.alumni.repos.CartItemRepository;
import com.mjt.tu.alumni.repos.PhotoRepository;
import com.mjt.tu.alumni.repos.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    private final CartItemRepository cartItemRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    @PostMapping("/cartItems/create")
    private void createCartItem(@RequestBody CartItemDto item) {
        User user = userRepository.findById(item.userName).orElseThrow();
        CartItem newItem = new CartItem(item.price, item.type, user);
        Photo photo = photoRepository.findById(item.fileName).orElseThrow();
        newItem.setImage(photo);
        user.addCartItem(newItem);
        this.cartItemRepository.save(newItem);
        this.userRepository.save(user);
    }

    @GetMapping("/cartItems/{id}")
    private CartItem getCartItem(@PathVariable("id") long id) {
        return cartItemRepository.findById(id).get();
    }

    @DeleteMapping("/cartItems/{id}")
    private String deletCartItem(@PathVariable("id") long id) {
        if (!cartItemRepository.existsById(id)) {
            return "No such item.";
        }

        cartItemRepository.delete(cartItemRepository.findById(id).get());
        return "Deleted.";
    }

    @PostMapping("/albums/create/{userId}")
    private void createAlbum(@RequestBody Album item, @PathVariable("userId") String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        item.setOwner(user);
        user.addAlbum(item);
        this.albumRepository.save(item);
        this.userRepository.save(user);
    }

    @GetMapping("/albums/{id}")
    private Album getAlbum(@PathVariable("id") long id) {
        return albumRepository.findById(id).get();
    }

    @DeleteMapping("/albums/{id}")
    private String deletAlbum(@PathVariable("id") long id) {
        if (!albumRepository.existsById(id)) {
            return "No such group.";
        }

        albumRepository.delete(albumRepository.findById(id).get());
        return "Deleted.";
    }

}
