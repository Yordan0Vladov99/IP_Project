package com.mjt.tu.alumni.dtos;

import java.time.LocalDateTime;

import com.mjt.tu.alumni.models.Status;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemRequestResponse {
    public long id;
    public Status status;
    public String img;
    public String description;
    public LocalDateTime created;
}
