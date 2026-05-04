package org.persapiens.account.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationWebTest;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IntegrationWebTest
class JwtFactoryIT {

	@Autowired
	private JwtProperties jwtProperties;

	@Test
	void tokenExpired() {
		JwtProperties newJwtProperties = new JwtProperties(this.jwtProperties.secretKey(), 0);
		JwtFactory jwtFactory = new JwtFactory(newJwtProperties);
		String token = jwtFactory.generateToken("username");
		assertThatExceptionOfType(ExpiredJwtException.class)
				.isThrownBy(() -> jwtFactory.extractUsername(token));
	}

}
