package org.persapiens.account.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.jwt")
public record JwtProperties(String secretKey, long expirationTime) {
}
