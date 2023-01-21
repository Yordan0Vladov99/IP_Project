package com.mjt.tu.alumni.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjt.tu.alumni.dtos.GroupDto;
import com.mjt.tu.alumni.dtos.GroupLinkDto;
import com.mjt.tu.alumni.dtos.PhotoDto;
import com.mjt.tu.alumni.models.Group;
import com.mjt.tu.alumni.models.JoinRequest;
import com.mjt.tu.alumni.models.Status;
import com.mjt.tu.alumni.models.User;
import com.mjt.tu.alumni.repos.GroupRepository;
import com.mjt.tu.alumni.repos.UserRepository;
import com.mjt.tu.alumni.security.auth.GroupRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    public void registerGroup(GroupRequest groupRequest) {
        Group group = new Group();
        group.setName(groupRequest.getGroup());
        if (groupRequest.getParent() != 0) {
            Group parent = groupRepository.findById(groupRequest.getParent()).orElseThrow();
            group.setParent(parent);
        }
        long id = groupRepository.save(group).getId();
        addMember(id, groupRequest.getUser());
    }

    public boolean groupExists(Group group) {
        return groupRepository.existsById(group.getId());
    }

    public String updateGroup(Group group) {
        if (!groupExists(group)) {
            return "No such group.";
        }
        Group existingGroup = groupRepository.findById(group.getId()).get();
        existingGroup.setName(group.getName());
        groupRepository.save(existingGroup);
        return "Updated.";
    }

    public Set<GroupLinkDto> getTopGroups() {
        return Set.copyOf(groupRepository.findTop10ByOrderByMembers().stream()
                .map(group -> new GroupLinkDto(group.getId(), group.getName())).toList());
    }

    public String addMember(long groupId, String memberId) {
        User user = userRepository.findById(memberId).orElseThrow();
        Group group = groupRepository.findById(groupId).orElseThrow();
        group.addMember(user);
        // System.out.println("\n".repeat(10) + group + "\n".repeat(10));
        groupRepository.save(group);

        user.addGroup(group);
        userRepository.save(user);

        return "Added";
    }

    public GroupDto getGroup(long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        Set<String> photos = Set.copyOf(group.getPhotos().stream().map(photo -> photo.getFileName()).toList());
        Set<String> members = Set.copyOf(group.getMembers().stream().map(member -> member.getId()).toList());
        return new GroupDto(group.getName(), photos, members);
    }

    public Group getGroupParent(long id) {
        Group group = groupRepository.findById(id).get();
        return group.getParent();
    }

    public Set<GroupLinkDto> getGroupChildren(long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        return Set.copyOf(
                group.getChildren().stream().map(child -> new GroupLinkDto(child.getId(), child.getName())).toList());
    }

    public Set<PhotoDto> getGroupPhotos(long id) {
        Group group = groupRepository.findById(id).get();
        return Set.copyOf(group.getPhotos().stream()
                .map(photo -> new PhotoDto(photo.getFileName(), photo.getFileExtension(), photo.getUser().getId(),
                        photo.getCreated()))
                .toList());
    }

    public List<GroupLinkDto> getPath(long id) {
        List<GroupLinkDto> path = new ArrayList<>();
        Group group = groupRepository.findById(id).orElseThrow();
        path.add(new GroupLinkDto(group.getId(), group.getName()));
        while (group.getParent() != null) {
            group = group.getParent();
            path.add(new GroupLinkDto(group.getId(), group.getName()));
        }
        Collections.reverse(path);
        return path;
    }

    public Set<JoinRequest> getGroupJoinRequests(long id) {
        Group group = groupRepository.findById(id).get();
        Set<JoinRequest> groupRequests = group.getRequests();
        ArrayList<JoinRequest> pendingRequests = new ArrayList<>();
        for (JoinRequest req : groupRequests) {
            if (req.getStatus() == Status.Pending) {
                pendingRequests.add(req);
            }
        }

        Set<JoinRequest> pJoinRequestsSet = Set.copyOf(pendingRequests);
        return pJoinRequestsSet;
    }

    public String deleteGroup(long id) {
        if (!groupRepository.existsById(id)) {
            return "No such group.";
        }

        groupRepository.delete(groupRepository.findById(id).get());
        return "Deleted.";
    }
}
