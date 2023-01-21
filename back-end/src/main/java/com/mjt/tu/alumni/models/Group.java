package com.mjt.tu.alumni.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "alumni_group")
public class Group {
    public Group() {
        this.members = new HashSet<>();
        this.requests = new HashSet<>();
        this.children = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Group parent;

    @OneToMany(mappedBy = "parent")
    private Set<Group> children;

    @OneToMany(mappedBy = "group")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "group")
    private Set<JoinRequest> requests;

    @ManyToMany(mappedBy = "groups")
    Set<User> members;

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public Set<Group> getChildren() {
        return children;
    }

    public void setChildren(Set<Group> children) {
        this.children = children;
    }

    public Set<JoinRequest> getRequests() {
        return requests;
    }

    public void setRequests(Set<JoinRequest> requests) {
        this.requests = requests;
    }

    public void addJoinRequest(JoinRequest request) {
        this.requests.add(request);
    }

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
        this.members = new HashSet<>();
        this.requests = new HashSet<>();
        this.children = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    public void addMember(User member) {
        this.members.add(member);
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", parent=" + parent + ", children=" + children + ", photos="
                + photos + ", requests=" + requests + ", members=" + members + "]";
    }

}
