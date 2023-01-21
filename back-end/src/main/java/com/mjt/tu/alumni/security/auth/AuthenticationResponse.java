package com.mjt.tu.alumni.security.auth;

import com.mjt.tu.alumni.models.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private UserType userType;
    private String token;
}
