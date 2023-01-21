package com.mjt.tu.alumni.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDto {
    public String name;
    public Set<String> photos;
    public Set<Long> groups;
}