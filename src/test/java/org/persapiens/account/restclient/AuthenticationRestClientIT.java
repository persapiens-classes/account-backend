package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationWebTest;
import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.UserCredentialsProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IntegrationWebTest
class AuthenticationRestClientIT extends RestClientIT {

	@Autowired
	private UserCredentialsProperties userCredentialsProperties;

	@Test
	void loginValid() {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO(this.userCredentialsProperties.name(),
				this.userCredentialsProperties.password());

		var authenticationRestClient = authenticationRestClient();
		var response = authenticationRestClient.login(loginRequestDTO);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().login()).isNotNull();
		assertThat(response.getBody().token()).isNotBlank();
		assertThat(response.getBody().expiresIn()).isPositive();
	}

	@Test
	void meUnauthorized() {
		var authenticationRestClient = authenticationRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(authenticationRestClient::me)
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN));
	}

	@Test
	void logoutNoCookieHeader() {
		var authenticationRestClient = authenticatedAuthenticationRestClient();
		var response = authenticationRestClient.logout();
		assertThat(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE)).isNull();
	}

	@Test
	void loginInvalid() {
		loginInvalid("", "");
		loginInvalid("invalid user", "");
		loginInvalid("", this.userCredentialsProperties.password());
		loginInvalid("invalid user", this.userCredentialsProperties.password());
	}

	void loginInvalid(String username, String password) {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);

		var authenticationRestClient = authenticationRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> authenticationRestClient.login(loginRequestDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED));
	}

}
