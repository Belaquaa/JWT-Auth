package com.belaquaa.jwt.payload.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class JwtResponse {
    private final String token;
    private final Long id;
    private final String username;
    private final Integer age;
    private final String email;
    private final List<String> roles;

    private final String type = "Bearer";
}
