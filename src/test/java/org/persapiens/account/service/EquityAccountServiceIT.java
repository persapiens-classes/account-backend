package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.EquityAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EquityAccountServiceIT {

	@Autowired
	private EquityAccountRepository equityAccountRepository;

	@Autowired
	private AccountService<EquityAccount, EquityCategory> equityAccountService;

	@Autowired
	private EquityAccountDTOFactory equityAccountDTOFactory;

	@Autowired
	private EquityCategoryDTOFactory categoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.equityAccountService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		AccountDTO equityAccountDTO = this.equityAccountDTOFactory.checking();

		// verify the results
		assertThat(this.equityAccountService.findByDescription(equityAccountDTO.description()))
			.isEqualTo(equityAccountDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		AccountDTO equityAccountDTO = this.equityAccountDTOFactory.equityAccountDTO("UNIQUE EquityAccount",
				this.categoryDTOFactory.bank().description());

		// execute the operation to be tested
		this.equityAccountService.deleteByDescription(equityAccountDTO.description());

		// verify the results
		assertThat(this.equityAccountRepository.findByDescription(equityAccountDTO.description())).isNotPresent();
	}

}
