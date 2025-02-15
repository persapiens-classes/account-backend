package org.persapiens.account.restclient;

import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.LoginResponseDTO;
import org.persapiens.account.security.UserCredentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

@ConditionalOnWebApplication
@Component
class JwtTokenFactory {

	@Autowired
	private UserCredentials userCredentials;

	private String jwtToken;

	String getJwtToken(AuthenticationRestClient authenticationRestClient) {
		if (this.jwtToken == null) {
			LoginRequestDTO loginRequest = LoginRequestDTO.builder()
				.username(this.userCredentials.getName())
				.password(this.userCredentials.getPassword())
				.build();

			LoginResponseDTO loginResponse = authenticationRestClient.login(loginRequest);

			if (loginResponse != null) {
				this.jwtToken = loginResponse.getToken();
			}
		}
		return this.jwtToken;
	}

}
