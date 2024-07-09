package com.belaquaa.jwt.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignupRequest(
        @NotBlank(message = "Username is mandatory") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String username,
        @NotNull(message = "Age is mandatory") @Min(value = 0, message = "Age must be greater than 0") Integer age,
        @NotBlank(message = "Email is mandatory") @Size(max = 120, message = "Email must be less than 120 characters") @Email(message = "Email should be valid") String email,
        Set<String> role, @Size(min = 3, message = "Password must be at least 3 characters") String password) {
}
