package org.persapiens.account.security;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.jwt")
@Getter
@Setter
public class JwtProperties {

	private String secretKey;

	private long expirationTime;

}
