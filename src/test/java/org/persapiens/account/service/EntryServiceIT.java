package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EntryServiceIT {

	@Autowired
	private EntryService entryService;

	@Autowired
	private OwnerDTOFactory ownerDTOFactory;

	@Autowired
	private EquityAccountDTOFactory equityAccountDTOFactory;

	@Autowired
	private CreditAccountDTOFactory creditAccountDTOFactory;

	@Autowired
	private DebitAccountDTOFactory debitAccountDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.entryService).isNotNull();
	}

	@Test
	void entryWithInvalidInAccount() {
		EntryInsertUpdateDTO entry = new EntryInsertUpdateDTO(this.ownerDTOFactory.father().name(), LocalDateTime.now(),
				this.creditAccountDTOFactory.internship().description(),
				this.equityAccountDTOFactory.savings().description(), BigDecimal.TEN, "");

		Assertions.assertThatThrownBy(() -> this.entryService.insert(entry))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void entryWithInvalidOutAccount() {
		EntryInsertUpdateDTO entry = new EntryInsertUpdateDTO(this.ownerDTOFactory.father().name(), LocalDateTime.now(),
				this.equityAccountDTOFactory.savings().description(),
				this.debitAccountDTOFactory.gasoline().description(), BigDecimal.ZERO, "");

		Assertions.assertThatThrownBy(() -> this.entryService.insert(entry))
			.isInstanceOf(IllegalArgumentException.class);
	}

}
