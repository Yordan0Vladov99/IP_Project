package com.mjt.tu.alumni.models;

import jakarta.persistence.*;

@Entity
@Table(name = "join_requests")
public class JoinRequest extends Request {
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
