package org.persapiens.account.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BalanceServiceIT {

	@Autowired
	private CreditEntryDTOFactory creditEntryDTOFactory;

	@Autowired
	private DebitEntryDTOFactory debitEntryDTOFactory;

	@Autowired
	private OwnerDTOFactory ownerDTOFactory;

	@Autowired
	private BalanceService balanceService;

	@Autowired
	private OwnerEquityAccountInitialValueDTOFactory ownerEquityAccountInitialValueDTOFactory;

	@Autowired
	private EquityAccountDTOFactory equityAccountDTOFactory;

	@Autowired
	private CreditAccountDTOFactory creditAccountDTOFactory;

	@Autowired
	private DebitAccountDTOFactory debitAccountDTOFactory;

	@Test
	void balance800() {
		// create test environment
		OwnerDTO father = this.ownerDTOFactory.father();

		AccountDTO wallet = this.equityAccountDTOFactory.wallet();

		AccountDTO gasoline = this.debitAccountDTOFactory.gasoline();

		AccountDTO internship = this.creditAccountDTOFactory.internship();

		this.ownerEquityAccountInitialValueDTOFactory.ownerEquityAccountInitialValueDTO(father, wallet,
				new BigDecimal(1000));

		this.debitEntryDTOFactory.debitEntryDTO(father, gasoline, wallet, new BigDecimal(500));
		this.creditEntryDTOFactory.creditEntryDTO(father, wallet, internship, new BigDecimal(300));

		// run the operation to be tested
		// verify the results
		assertThat(this.balanceService.balance(father.name(), wallet.description()))
			.isEqualTo(new BigDecimal(800).setScale(2));
	}

}
