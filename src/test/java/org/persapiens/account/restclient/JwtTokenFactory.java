package org.persapiens.account.restclient;

import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.LoginResponseDTO;
import org.persapiens.account.security.UserCredentialsProperties;
import org.persapiens.account.security.AuthCookie;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@ConditionalOnWebApplication
@Component
class JwtTokenFactory {

	private UserCredentialsProperties userCredentialsProperties;

	private String authCookie;

	JwtTokenFactory(UserCredentialsProperties userCredentialsProperties) {
		this.userCredentialsProperties = userCredentialsProperties;
	}

	String getAuthCookie(AuthenticationRestClient authenticationRestClient) {
		if (this.authCookie == null) {
			LoginRequestDTO loginRequest = new LoginRequestDTO(
				this.userCredentialsProperties.name(),
				this.userCredentialsProperties.password());

			ResponseEntity<LoginResponseDTO> loginResponse = authenticationRestClient.login(loginRequest);
			String setCookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
			if (setCookie != null) {
				this.authCookie = extractCookieValue(setCookie);
			}
		}
		return this.authCookie;
	}

	private String extractCookieValue(String setCookie) {
		String[] parts = setCookie.split(";", 2);
		if (parts.length == 0 || !parts[0].startsWith(AuthCookie.NAME + "=")) {
			return null;
		}
		return parts[0];
	}

}
