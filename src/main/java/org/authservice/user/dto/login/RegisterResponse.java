package org.authservice.user.dto.login;

public record RegisterResponse(String token, String firstName, String lastName, String phoneNumber, String email, boolean active) {
}