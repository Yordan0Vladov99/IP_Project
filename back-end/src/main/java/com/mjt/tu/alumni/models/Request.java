package com.mjt.tu.alumni.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@MappedSuperclass
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Request() {
        this.created = LocalDateTime.now();
    }

    public Request(String description, Status status, User sender) {
        this.description = description;
        this.status = status;
        this.sender = sender;
        this.created = LocalDateTime.now();
    }

    @Column(length = 140)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Status status;

    private LocalDateTime created;

    public LocalDateTime getCreated() {
        return created;
    }

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
