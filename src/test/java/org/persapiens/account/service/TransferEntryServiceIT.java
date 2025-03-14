package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TransferEntryServiceIT {

	@Autowired
	private OwnerDTOFactory ownerDTOFactory;

	@Autowired
	private EquityAccountDTOFactory equityAccountDTOFactory;

	@Autowired
	private TransferEntryService transferEntryService;

	@Test
	void transfer10FromFatherToAunt() {
		EntryInsertUpdateDTO entry = new EntryInsertUpdateDTO(this.ownerDTOFactory.father().name(), 
				this.ownerDTOFactory.aunt().name(), 
				LocalDateTime.now(),
				this.equityAccountDTOFactory.checking().description(),
				this.equityAccountDTOFactory.savings().description(), BigDecimal.TEN, "");

		this.transferEntryService.insert(entry);

		assertThat(this.transferEntryService.debitSum(this.ownerDTOFactory.father().name(),
				this.equityAccountDTOFactory.checking().description()))
			.isEqualTo(BigDecimal.TEN.setScale(2));

		assertThat(this.transferEntryService.creditSum(this.ownerDTOFactory.aunt().name(),
				this.equityAccountDTOFactory.savings().description()))
			.isEqualTo(BigDecimal.TEN.setScale(2));
	}

}
