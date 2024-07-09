package com.belaquaa.jwt.payload.response;

import java.util.List;

public record JwtResponse(String token, Long id, String username, Integer age, String email, List<String> roles) {
}
