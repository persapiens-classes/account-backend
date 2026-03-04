package org.persapiens.account.security;

public record LoginResponseDTO(String token, long expiresIn) {
}
