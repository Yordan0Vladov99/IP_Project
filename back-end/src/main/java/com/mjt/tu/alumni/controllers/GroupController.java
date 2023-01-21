package com.mjt.tu.alumni.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mjt.tu.alumni.dtos.GroupDto;
import com.mjt.tu.alumni.dtos.GroupLinkDto;
import com.mjt.tu.alumni.dtos.PhotoDto;
import com.mjt.tu.alumni.models.Group;
import com.mjt.tu.alumni.models.Photo;
import com.mjt.tu.alumni.repos.GroupRepository;
import com.mjt.tu.alumni.security.auth.GroupRequest;
import com.mjt.tu.alumni.services.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    public GroupController(GroupRepository groupRepository, GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    private void createGroup(@RequestBody GroupRequest group) {
        this.groupService.registerGroup(group);
    }

    @PutMapping("/update")
    private String updateGroup(@RequestBody Group group) {
        return groupService.updateGroup(group);
    }

    @GetMapping
    private Set<GroupLinkDto> getTopGroups() {
        return groupService.getTopGroups();
    }

    @GetMapping("/{id}")
    private GroupDto getGroup(@PathVariable("id") long id) {
        return groupService.getGroup(id);
    }

    @GetMapping("/{id}/photos")
    private Set<PhotoDto> getGroupPhotos(@PathVariable("id") long id) {
        return groupService.getGroupPhotos(id);
    }

    @GetMapping("/{id}/path")
    private List<GroupLinkDto> getPath(@PathVariable("id") long id) {
        return groupService.getPath(id);
    }

    @GetMapping("/{id}/children")
    private Set<GroupLinkDto> getChildren(@PathVariable("id") long id) {
        return groupService.getGroupChildren(id);
    }

    @PutMapping("{id}/addMember/{memberId}")
    private String addMember(@PathVariable("id") long groupId, @PathVariable("memberId") String memId) {
        return this.groupService.addMember(groupId, memId);
    }

    @DeleteMapping("/removeGroup/{id}")
    private String deleteUser(@PathVariable("id") long id) {
        return groupService.deleteGroup(id);
    }

}
