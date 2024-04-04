package org.authservice.user.dto.login;



public record LoginResponse(String token, String firstName, String lastName, String phoneNumber, String email,  boolean active) {
}