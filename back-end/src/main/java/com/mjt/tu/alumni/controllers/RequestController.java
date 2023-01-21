package com.mjt.tu.alumni.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mjt.tu.alumni.dtos.ItemRequestDto;
import com.mjt.tu.alumni.dtos.PhotoSessionRequest;
import com.mjt.tu.alumni.models.Group;
import com.mjt.tu.alumni.models.ItemRequest;
import com.mjt.tu.alumni.models.JoinRequest;
import com.mjt.tu.alumni.models.Photo;
import com.mjt.tu.alumni.models.PhotoSession;
import com.mjt.tu.alumni.models.Status;
import com.mjt.tu.alumni.models.User;
import com.mjt.tu.alumni.repos.GroupRepository;
import com.mjt.tu.alumni.repos.ItemRequestRepository;
import com.mjt.tu.alumni.repos.JoinRequestRepository;
import com.mjt.tu.alumni.repos.PhotoRepository;
import com.mjt.tu.alumni.repos.PhotoSessionRepository;
import com.mjt.tu.alumni.repos.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    @Autowired
    private final JoinRequestRepository joinRequestRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final PhotoSessionRepository photoSessionRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @PostMapping("/join/create/{groupId}/{userId}")
    private void createJoinRequest(@RequestBody JoinRequest request,
            @PathVariable("groupId") long groupId, @PathVariable("userId") String userId) {

        User user = userRepository.findById(userId).orElseThrow();
        Group group = groupRepository.findById(groupId).orElseThrow();
        request.setSender(user);
        user.addJoinRequest(request);
        group.addJoinRequest(request);
        joinRequestRepository.save(request);
        userRepository.save(user);
        groupRepository.save(group);

    }

    @GetMapping("/join/{id}")
    private JoinRequest getJoinRequest(@PathVariable("id") long id) {
        return joinRequestRepository.findById(id).get();
    }

    @PutMapping("/join/{id}")
    private String updateJoinRequest(@RequestBody JoinRequest request) {
        if (!joinRequestRepository.existsById(request.getId())) {
            return "No such request.";
        }

        JoinRequest existingReq = joinRequestRepository.findById(request.getId()).get();
        existingReq.setStatus(request.getStatus());
        return "Updated.";
    }

    @DeleteMapping("/join/{id}")
    private String deleteJoinRequest(@PathVariable("id") long id) {
        if (!joinRequestRepository.existsById(id)) {
            return "No such request.";
        }

        joinRequestRepository.delete(joinRequestRepository.findById(id).get());
        return "Deleted.";
    }

    @PostMapping("/item/create")
    private void createItemRequest(@RequestBody ItemRequestDto request) {
        User user = userRepository.findById(request.user).orElseThrow();
        User receiver = userRepository.findById("yordan_vladov@abv.bg").orElseThrow();
        Photo photo = photoRepository.findById(request.img).orElseThrow();

        ItemRequest itemReq = new ItemRequest(request.description, Status.Pending, user, receiver, photo);
        user.addSentItemRequest(itemReq);
        receiver.addReceivedItemRequest(itemReq);
        this.userRepository.save(user);
        this.userRepository.save(receiver);
        this.itemRequestRepository.save(itemReq);
    }

    @GetMapping("/item/{id}")
    private ItemRequest getItemRequest(@PathVariable("id") long id) {
        return itemRequestRepository.findById(id).get();
    }

    @PutMapping("/item/{status}/{id}")
    private String updateItemRequest(@PathVariable("status") String status, @PathVariable("id") long id) {
        if (!itemRequestRepository.existsById(id)) {
            return "No such request.";
        }

        ItemRequest existingReq = itemRequestRepository.findById(id).orElseThrow();
        if (status.equals("accept")) {
            existingReq.setStatus(Status.Accepted);
        } else if (status.equals("deny")) {
            existingReq.setStatus(Status.Denied);
        }
        itemRequestRepository.save(existingReq);
        return "Updated.";
    }

    @DeleteMapping("/item/{id}")
    private String deleteItemRequest(@PathVariable("id") long id) {
        if (!itemRequestRepository.existsById(id)) {
            return "No such request.";
        }

        itemRequestRepository.delete(itemRequestRepository.findById(id).get());
        return "Deleted.";
    }

    @PostMapping("/session/create")
    private void createPhotoSession(@RequestBody PhotoSessionRequest request) {
        User sender = userRepository.findById(request.sender).orElseThrow();
        User receiver = userRepository.findById("yordan_vladov@abv.bg").orElseThrow();

        String str = request.date + " " + request.time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        PhotoSession session = new PhotoSession(request.description, Status.Pending, sender, request.people, dateTime,
                request.location, receiver);
        sender.addSentSessionRequest(session);
        receiver.addReceivedSessionRequest(session);

        photoSessionRepository.save(session);
        userRepository.save(sender);
        userRepository.save(receiver);

    }

    @GetMapping("/session/{id}")
    private PhotoSession getPhotoSession(@PathVariable("id") long id) {
        return photoSessionRepository.findById(id).get();
    }

    @PutMapping("/session/{id}")
    private String updatePhotoSession(@RequestBody PhotoSession request) {
        if (!photoSessionRepository.existsById(request.getId())) {
            return "No such request.";
        }

        PhotoSession existingReq = photoSessionRepository.findById(request.getId()).get();
        existingReq.setStatus(request.getStatus());
        return "Updated.";
    }

    @DeleteMapping("/session/{id}")
    private String deletePhotoSession(@PathVariable("id") long id) {
        if (!photoSessionRepository.existsById(id)) {
            return "No such request.";
        }

        photoSessionRepository.delete(photoSessionRepository.findById(id).get());
        return "Deleted.";
    }

}
