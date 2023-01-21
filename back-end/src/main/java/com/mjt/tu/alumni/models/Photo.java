package com.mjt.tu.alumni.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "photo")
public class Photo {
    public Photo() {
    }

    public Photo(String fileName, String fileExtension) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.created = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "name", length = 36)
    private String fileName;

    @Column(name = "extension", length = 5)
    private String fileExtension;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Photo [fileName=" + fileName + ", created=" + created + ", user=" + user.getId()
                + ", group="
                + group + "]";
    }

}
