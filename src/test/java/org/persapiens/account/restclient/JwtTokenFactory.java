package org.persapiens.account.restclient;

import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.LoginResponseDTO;
import org.persapiens.account.security.UserCredentialsProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@ConditionalOnWebApplication
@Component
class JwtTokenFactory {

	private UserCredentialsProperties userCredentialsProperties;

	private String token;

	JwtTokenFactory(UserCredentialsProperties userCredentialsProperties) {
		this.userCredentialsProperties = userCredentialsProperties;
	}

	String getToken(AuthenticationRestClient authenticationRestClient) {
		if (this.token == null) {
			LoginRequestDTO loginRequest = new LoginRequestDTO(this.userCredentialsProperties.name(),
					this.userCredentialsProperties.password());

			ResponseEntity<LoginResponseDTO> loginResponse = authenticationRestClient.login(loginRequest);
			LoginResponseDTO loginResponseBody = loginResponse.getBody();
			if (loginResponseBody != null) {
				this.token = loginResponseBody.token();
			}
		}
		return this.token;
	}

}
