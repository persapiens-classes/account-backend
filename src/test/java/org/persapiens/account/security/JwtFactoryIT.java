package org.persapiens.account.security;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtFactoryIT {

	@Autowired
	private JwtProperties jwtProperties;

	@Test
	void tokenExpired() {
		JwtProperties newJwtProperties = new JwtProperties();
		newJwtProperties.setSecretKey(this.jwtProperties.getSecretKey());
		newJwtProperties.setExpirationTime(0);
		JwtFactory jwtFactory = new JwtFactory(newJwtProperties);
		String token = jwtFactory.generateToken("username");
		assertThat(jwtFactory.isTokenValid(token, "username")).isFalse();
	}

	@Test
	void tokenWrongUser() {
		JwtFactory jwtFactory = new JwtFactory(this.jwtProperties);
		String token = jwtFactory.generateToken("username");
		assertThat(jwtFactory.isTokenValid(token, "other username")).isFalse();
	}

}
