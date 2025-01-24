package org.persapiens.account;

import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("user-credentials")
@Getter
@Setter
public class UserCredentials {

	private String name;

	private String password;

	@SuppressFBWarnings("EI_EXPOSE_REP")
	private List<String> authorities;

}
