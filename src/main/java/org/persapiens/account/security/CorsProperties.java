package org.persapiens.account.security;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.cors")
@Getter
@Setter
public class CorsProperties {

	private String allowedOriginPatterns;

}
