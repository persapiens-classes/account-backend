package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.CreditAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

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
	void findOneByExample() {
		// create test environment
		CreditAccount creditAccount = this.creditAccountFactory.internship();

		CreditAccount creditAccountExemplo = CreditAccount.builder()
			.description(CreditAccountConstants.INTERNSHIP)
			.category(Category.builder().description(CategoryConstants.SALARY).build())
			.build();

		// execute the operation to be tested
		// verify the results
		assertThat(this.creditAccountRepository.findOne(Example.of(creditAccountExemplo)).get())
			.isEqualTo(creditAccount);
	}

}
