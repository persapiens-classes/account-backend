package org.persapiens.account.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
		assertThatExceptionOfType(ExpiredJwtException.class)
				.isThrownBy(() -> jwtFactory.extractUsername(token));
	}

}
