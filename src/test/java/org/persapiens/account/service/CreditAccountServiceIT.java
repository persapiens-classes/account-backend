package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.CreditAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CreditAccountServiceIT {

	@Autowired
	private CreditAccountRepository creditAccountRepository;

	@Autowired
	private AccountService<CreditAccount, CreditCategory> creditAccountService;

	@Autowired
	private CreditAccountDTOFactory creditAccountDTOFactory;

	@Autowired
	private CreditCategoryDTOFactory categoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.creditAccountService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		AccountDTO creditAccountDTO = this.creditAccountDTOFactory.internship();

		// verify the results
		assertThat(this.creditAccountService.findByDescription(creditAccountDTO.description()))
			.isEqualTo(creditAccountDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		AccountDTO creditAccountDTO = this.creditAccountDTOFactory.creditAccountDTO("UNIQUE CreditAccount",
				this.categoryDTOFactory.salary().description());

		// execute the operation to be tested
		this.creditAccountService.deleteByDescription(creditAccountDTO.description());

		// verify the results
		assertThat(this.creditAccountRepository.findByDescription(creditAccountDTO.description())).isNotPresent();
	}

}
