package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EntryRestClientIT extends RestClientIT {

	private EntryInsertUpdateDTO entry() {
		String mother = owner(OwnerConstants.MOTHER).getName();
		String salary = category(CategoryConstants.SALARY).getDescription();
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP, salary).getDescription();
		String bank = category(CategoryConstants.BANK).getDescription();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).getDescription();

		return EntryInsertUpdateDTO.builder()
			.value(new BigDecimal(543))
			.note("saving the internship")
			.owner(mother)
			.inAccount(savings)
			.outAccount(internship)
			.date(LocalDateTime.now())
			.build();
	}

	@Test
	void insertOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		// verify insert operation
		assertThat(entryRestClient().insert(entryInsertDTO)).isNotNull();

		// verify findAll operation
		assertThat(entryRestClient().findAll()).isNotEmpty();
	}

	@Test
	void deleteOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		EntryDTO entryDTO = entryRestClient().insert(entryInsertDTO);

		assertThat(entryDTO.getId()).isGreaterThan(0);

		entryRestClient().deleteById(entryDTO.getId());

		assertThat(entryRestClient().findById(entryDTO.getId())).isEmpty();
	}

	@Test
	void updateOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		EntryDTO entryDTO = entryRestClient().insert(entryInsertDTO);

		EntryInsertUpdateDTO entryUpdate = EntryInsertUpdateDTO.builder()
			.value(entryDTO.getValue())
			.note("updated note")
			.owner(entryDTO.getOwner())
			.inAccount(entryDTO.getInAccount().getDescription())
			.outAccount(entryDTO.getOutAccount().getDescription())
			.date(entryDTO.getDate())
			.build();

		entryRestClient().update(entryDTO.getId(), entryUpdate);

		assertThat(entryRestClient().findById(entryDTO.getId()).get().getNote()).isEqualTo("updated note");
	}

}
