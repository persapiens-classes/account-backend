package org.persapiens.account.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TransferServiceIT {

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private TransferService transferService;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private EntryService entryService;

	@Test
	public void transfer10FromFatherToMother() {
		this.transferService.transfer(BigDecimal.TEN, this.ownerFactory.father(), this.equityAccountFactory.checking(),
				this.ownerFactory.mother(), this.equityAccountFactory.savings());

		assertThat(this.entryService.debitSum(this.ownerFactory.father(), this.equityAccountFactory.checking()))
			.isEqualTo(BigDecimal.TEN.setScale(2));

		assertThat(this.entryService.creditSum(this.ownerFactory.mother(), this.equityAccountFactory.savings()))
			.isEqualTo(BigDecimal.TEN.setScale(2));
	}

}
