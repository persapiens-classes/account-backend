package org.persapiens.account.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TransferServiceIT {

	@Autowired
	private OwnerDTOFactory ownerDTOFactory;

	@Autowired
	private TransferService transferService;

	@Autowired
	private EquityAccountDTOFactory equityAccountDTOFactory;

	@Autowired
	private EntryService entryService;

	@Test
	void transfer10FromFatherToAunt() {
		this.transferService.transfer(BigDecimal.TEN, this.ownerDTOFactory.father(),
				this.equityAccountDTOFactory.checking(), this.ownerDTOFactory.aunt(),
				this.equityAccountDTOFactory.savings());

		assertThat(this.entryService.debitSum(this.ownerDTOFactory.father(), this.equityAccountDTOFactory.checking()))
			.isEqualTo(BigDecimal.TEN.setScale(2));

		assertThat(this.entryService.creditSum(this.ownerDTOFactory.aunt(), this.equityAccountDTOFactory.savings()))
			.isEqualTo(BigDecimal.TEN.setScale(2));
	}

}
