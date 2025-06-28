package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;
import org.persapiens.account.persistence.TransferEntryRepository;

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
	private EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> transferEntryService;

	@Autowired
	private TransferEntryRepository transferEntryRepository;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Test
	void transfer10FromFatherToAunt() {
		EntryInsertUpdateDTO entry = new EntryInsertUpdateDTO(this.ownerDTOFactory.aunt().name(),
				this.ownerDTOFactory.father().name(), LocalDateTime.now(),
				this.equityAccountDTOFactory.savings().description(),
				this.equityAccountDTOFactory.checking().description(), BigDecimal.TEN, "");

		this.transferEntryService.insert(entry);

		assertThat(this.transferEntryRepository.debitSum(this.ownerFactory.father(),
				this.equityAccountFactory.checking()))
			.isEqualTo(BigDecimal.TEN.setScale(2));

		assertThat(this.transferEntryRepository.creditSum(this.ownerFactory.aunt(),
				this.equityAccountFactory.savings()))
			.isEqualTo(BigDecimal.TEN.setScale(2));
	}

}
