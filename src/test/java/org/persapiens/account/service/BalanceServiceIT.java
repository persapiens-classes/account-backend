package org.persapiens.account.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BalanceServiceIT {

	@Autowired
	private EntryDTOFactory entryDTOFactory;

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

		EquityAccountDTO wallet = this.equityAccountDTOFactory.wallet();

		DebitAccountDTO gasoline = this.debitAccountDTOFactory.gasoline();

		CreditAccountDTO internship = this.creditAccountDTOFactory.internship();

		this.ownerEquityAccountInitialValueDTOFactory.ownerEquityAccountInitialValueDTO(father, wallet,
				new BigDecimal(1000));

		this.entryDTOFactory.entryDTO(father, gasoline, wallet, new BigDecimal(500));
		this.entryDTOFactory.entryDTO(father, wallet, internship, new BigDecimal(300));

		// run the operation to be tested
		// verify the results
		assertThat(this.balanceService.balance(father.name(), wallet.getDescription()))
			.isEqualTo(new BigDecimal(800).setScale(2));
	}

}
