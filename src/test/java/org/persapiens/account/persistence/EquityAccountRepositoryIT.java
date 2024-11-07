package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.EquityAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
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
		EquityAccount equityAccount = this.equityAccountFactory.savings();

		EquityAccount equityAccountExemplo = EquityAccount.builder()
			.description(EquityAccountConstants.SAVINGS)
			.category(Category.builder().description(CategoryConstants.BANK).build())
			.build();

		// execute the operation to be tested
		// verify the results
		assertThat(this.equityAccountRepository.count(Example.of(equityAccountExemplo))).isEqualTo(1);
	}

}
