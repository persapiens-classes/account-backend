package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.DebitAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DebitAccountServiceIT {

	@Autowired
	private DebitAccountRepository debitAccountRepository;

	@Autowired
	private DebitAccountService debitAccountService;

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
