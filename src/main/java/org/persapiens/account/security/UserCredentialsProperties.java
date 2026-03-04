package org.persapiens.account.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.user-credentials")
public record UserCredentialsProperties(String name, String password, List<String> authorities) {
}
