package org.persapiens.account;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AccountApplicationIT {

	@Test
	void contextLoads() {
		// Smoke test to check if application starts up.
	}

}
