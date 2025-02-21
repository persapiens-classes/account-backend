package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.DebitAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DebitAccountRepositoryIT {

	@Autowired
	private DebitAccountRepository debitAccountRepository;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.debitAccountRepository).isNotNull();
	}

	@Test
	void findOneByExample() {
		// create test environment
		DebitAccount debitAccount = this.debitAccountFactory.gasoline();

		// execute the operation to be tested
		// verify the results
		assertThat(this.debitAccountRepository.findByDescription(debitAccount.getDescription())).isPresent();
	}

}
