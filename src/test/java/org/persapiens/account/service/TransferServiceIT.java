package org.persapiens.account.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TransferServiceIT {

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private TransferService transferService;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private EntryService entryService;

	@Test
	void transfer10FromFatherToAunt() {
		this.transferService.transfer(BigDecimal.TEN, this.ownerFactory.father(), this.equityAccountFactory.checking(),
				this.ownerFactory.aunt(), this.equityAccountFactory.savings());

		assertThat(this.entryService.debitSum(this.ownerFactory.father(), this.equityAccountFactory.checking()))
			.isEqualTo(BigDecimal.TEN.setScale(2));

		assertThat(this.entryService.creditSum(this.ownerFactory.aunt(), this.equityAccountFactory.savings()))
			.isEqualTo(BigDecimal.TEN.setScale(2));
	}

}
