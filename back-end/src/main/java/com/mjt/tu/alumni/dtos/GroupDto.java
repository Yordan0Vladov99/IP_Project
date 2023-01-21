package com.mjt.tu.alumni.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupDto {
    public String name;
    public Set<String> photos;
    public Set<String> members;
}
