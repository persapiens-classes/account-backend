package org.persapiens.account.restclient;

import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.LoginResponseDTO;
import org.persapiens.account.security.UserCredentialsProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

@ConditionalOnWebApplication
@Component
class JwtTokenFactory {

	private UserCredentialsProperties userCredentialsProperties;

	private String jwtToken;

	JwtTokenFactory(UserCredentialsProperties userCredentialsProperties) {
		this.userCredentialsProperties = userCredentialsProperties;
	}

	String getJwtToken(AuthenticationRestClient authenticationRestClient) {
		if (this.jwtToken == null) {
			LoginRequestDTO loginRequest = new LoginRequestDTO(
				this.userCredentialsProperties.name(),
				this.userCredentialsProperties.password());

			LoginResponseDTO loginResponse = authenticationRestClient.login(loginRequest);

			if (loginResponse != null) {
				this.jwtToken = loginResponse.token();
			}
		}
		return this.jwtToken;
	}

}
