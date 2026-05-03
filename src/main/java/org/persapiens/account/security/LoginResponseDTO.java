package org.persapiens.account.security;

public record LoginResponseDTO(String login, String token, long expiresIn) {
}
