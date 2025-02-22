package org.persapiens.account.security;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.user-credentials")
@Getter
@Setter
public class UserCredentialsProperties {

	private String name;

	private String password;

	private List<String> authorities;

}
