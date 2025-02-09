package org.persapiens.account.restclient;

import org.persapiens.account.security.LoginRequest;
import org.persapiens.account.security.LoginResponse;
import org.persapiens.account.security.UserCredentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@ConditionalOnWebApplication
@Component
class JwtTokenFactory {

	@Autowired
	private UserCredentials userCredentials;

	private String jwtToken;

	String getJwtToken(String protocol, String servername, int port) {
		if (this.jwtToken == null) {
			LoginRequest loginRequest = LoginRequest.builder()
				.username(this.userCredentials.getName())
				.password(this.userCredentials.getPassword())
				.build();

			String loginUrl = protocol + "://" + servername + ":" + port + "/login";

			RestClient restClient = RestClient.create();

			LoginResponse loginResponse = restClient.post()
				.uri(loginUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.body(loginRequest)
				.retrieve()
				.body(LoginResponse.class);

			if (loginResponse != null) {
				this.jwtToken = loginResponse.getToken();
			}
		}
		return this.jwtToken;
	}

}
