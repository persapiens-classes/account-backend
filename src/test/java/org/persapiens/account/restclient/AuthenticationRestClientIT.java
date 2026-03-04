package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.UserCredentialsProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationRestClientIT extends RestClientIT {

	@Autowired
	private UserCredentialsProperties userCredentialsProperties;

	@Test
	void loginValid() {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
			this.userCredentialsProperties.name(),
			this.userCredentialsProperties.password());

		var authenticationRestClient = authenticationRestClient();
		var response = authenticationRestClient.login(loginRequestDTO);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().login()).isNotNull();
		assertThat(response.getBody().expiresIn()).isPositive();
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
