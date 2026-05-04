package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.DebitAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DebitAccountServiceIT {

	@Autowired
	private DebitAccountRepository debitAccountRepository;

	@Autowired
	private AccountService<DebitAccount, DebitCategory> debitAccountService;

	@Autowired
	private DebitAccountDTOFactory debitAccountDTOFactory;

	@Autowired
	private DebitCategoryDTOFactory categoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.debitAccountService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		AccountDTO debitAccountDTO = this.debitAccountDTOFactory.gasoline();

		// verify the results
		assertThat(this.debitAccountService.findByDescription(debitAccountDTO.description()))
			.isEqualTo(debitAccountDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		AccountDTO debitAccountDTO = this.debitAccountDTOFactory.debitAccountDTO("UNIQUE DebitAccount",
				this.categoryDTOFactory.tax().description());

		// execute the operation to be tested
		this.debitAccountService.deleteByDescription(debitAccountDTO.description());

		// verify the results
		assertThat(this.debitAccountRepository.findByDescription(debitAccountDTO.description())).isNotPresent();
	}

}
