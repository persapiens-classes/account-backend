package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.EquityAccountConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EquityAccountRepositoryIT {

	@Autowired
	private EquityAccountRepository equityAccountRepository;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.equityAccountRepository).isNotNull();
	}

	@Test
	void countByExample() {
		// create test environment
		this.equityAccountFactory.savings();

		// execute the operation to be tested
		// verify the results
		assertThat(this.equityAccountRepository.findByDescription(EquityAccountConstants.SAVINGS)).isPresent();
	}

}
