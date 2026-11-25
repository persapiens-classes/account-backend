package org.persapiens.account;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class AccountApplicationIT {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		// Smoke test to check if application starts up.
		assertThat(this.applicationContext).isNotNull();
	}

}
