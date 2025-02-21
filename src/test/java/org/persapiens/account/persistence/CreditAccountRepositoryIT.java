package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.CreditAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreditAccountRepositoryIT {

	@Autowired
	private CreditAccountRepository creditAccountRepository;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.creditAccountRepository).isNotNull();
	}

	@Test
	void findByDescription() {
		// create test environment
		CreditAccount creditAccount = this.creditAccountFactory.internship();

		// execute the operation to be tested
		// verify the results
		assertThat(this.creditAccountRepository.findByDescription(creditAccount.getDescription())).isPresent();
	}

}
