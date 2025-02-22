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
		LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
			.username(this.userCredentialsProperties.getName())
			.password(this.userCredentialsProperties.getPassword())
			.build();

		assertThat(authenticationRestClient().login(loginRequestDTO).getToken()).isNotNull();
	}

	@Test
	void loginInvalid() {
		LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
			.username("invalid user")
			.password(this.userCredentialsProperties.getPassword())
			.build();

		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> authenticationRestClient().login(loginRequestDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED));
	}

}
