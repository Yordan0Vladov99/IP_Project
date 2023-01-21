package com.mjt.tu.alumni.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mjt.tu.alumni.dtos.CartItemResponse;
import com.mjt.tu.alumni.dtos.GroupLinkDto;
import com.mjt.tu.alumni.dtos.ItemRequestResponse;
import com.mjt.tu.alumni.dtos.PhotoSessionResponse;
import com.mjt.tu.alumni.dtos.UserDto;
import com.mjt.tu.alumni.security.auth.AuthenticationRequest;
import com.mjt.tu.alumni.security.auth.AuthenticationResponse;
import com.mjt.tu.alumni.security.auth.RegisterRequest;
import com.mjt.tu.alumni.security.auth.RegistrationResponse;
import com.mjt.tu.alumni.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authencticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PutMapping("/update")
    public ResponseEntity<AuthenticationResponse> update(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<Set<GroupLinkDto>> getUserGroups(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getUserGroups(id));
    }

    @GetMapping("/{id}/cartItems")
    public ResponseEntity<Set<CartItemResponse>> getUserCartItems(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getUserCartItems(id));
    }

    @GetMapping("/{id}/sentItemRequests")
    public ResponseEntity<Set<ItemRequestResponse>> getSentItemRequests(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getSentItemRequests(id));
    }

    @GetMapping("/{id}/receivedItemRequests")
    public ResponseEntity<Set<ItemRequestResponse>> getReceivedItemRequests(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getReceivedItemRequests(id));
    }

    @GetMapping("/{id}/sentSessionRequests")
    public ResponseEntity<Set<PhotoSessionResponse>> getSentSessionRequests(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getReceivedSessions(id));
    }

    @GetMapping("/{id}/receivedSessionRequests")
    public ResponseEntity<Set<PhotoSessionResponse>> getReceivedSessionRequests(@PathVariable("id") String id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.getReceivedSessions(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
