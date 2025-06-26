package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DebitEntryServiceIT {

	@Autowired
	private EntryService<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> debitEntryService;

	@Autowired
	private OwnerDTOFactory ownerDTOFactory;

	@Autowired
	private EquityAccountDTOFactory equityAccountDTOFactory;

	@Autowired
	private DebitAccountDTOFactory debitAccountDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.debitEntryService).isNotNull();
	}

	@Test
	void entryWithInvalidOutAccount() {
		EntryInsertUpdateDTO entry = new EntryInsertUpdateDTO(this.ownerDTOFactory.father().name(),
				this.ownerDTOFactory.father().name(), LocalDateTime.now(),
				this.equityAccountDTOFactory.savings().description(),
				this.debitAccountDTOFactory.gasoline().description(), BigDecimal.ZERO, "");

		Assertions.assertThatThrownBy(() -> this.debitEntryService.insert(entry)).isInstanceOf(BeanNotFoundException.class);
	}

}
