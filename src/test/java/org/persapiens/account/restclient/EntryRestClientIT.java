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
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

	private void invalidInsert(BigDecimal value, LocalDateTime date, String ownerName, String inAccountDescription,
			String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO entryInsertUpdateDTO = EntryInsertUpdateDTO.builder()
			.value(value)
			.note("invalid insert")
			.owner(ownerName)
			.inAccount(inAccountDescription)
			.outAccount(outAccountDescription)
			.date(date)
			.build();

		// verify insert operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient().insert(entryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		BigDecimal value = new BigDecimal(100);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner(OwnerConstants.MOTHER).getName();
		String salary = category(CategoryConstants.SALARY).getDescription();
		String outAccountDescription = creditAccount(CreditAccountConstants.INTERNSHIP, salary).getDescription();
		String bank = category(CategoryConstants.BANK).getDescription();
		String inAccountDescription = equityAccount(EquityAccountConstants.SAVINGS, bank).getDescription();

		// test blank fields
		invalidInsert(null, date, ownerName, inAccountDescription, outAccountDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(value, null, ownerName, inAccountDescription, outAccountDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, "", inAccountDescription, outAccountDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, ownerName, "", outAccountDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, ownerName, inAccountDescription, "", HttpStatus.BAD_REQUEST);

		// test fields
		invalidInsert(value, date, "invalid owner", inAccountDescription, outAccountDescription, HttpStatus.CONFLICT);
		invalidInsert(value, date, ownerName, "invalid in account", outAccountDescription, HttpStatus.CONFLICT);
		invalidInsert(value, date, ownerName, inAccountDescription, "invalid out account", HttpStatus.CONFLICT);

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
