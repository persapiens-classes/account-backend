package org.persapiens.account.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.cors")
public record CorsProperties(String allowedOriginPatterns) {
}
