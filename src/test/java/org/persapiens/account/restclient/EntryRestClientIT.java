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
		String mother = owner(OwnerConstants.MOTHER).name();
		String salary = category(CategoryConstants.SALARY).description();
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP, salary).getDescription();
		String bank = category(CategoryConstants.BANK).description();
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
		String ownerName = owner(OwnerConstants.MOTHER).name();
		String salary = category(CategoryConstants.SALARY).description();
		String outAccountDescription = creditAccount(CreditAccountConstants.INTERNSHIP, salary).getDescription();
		String bank = category(CategoryConstants.BANK).description();
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

		assertThat(entryRestClient().findById(entryDTO.getId()).getNote()).isEqualTo("updated note");
	}

	private void updateInvalid(Long id, BigDecimal value, LocalDateTime date, String ownerName,
			String inAccountDescription, String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO entryInsertUpdateDTO = EntryInsertUpdateDTO.builder()
			.value(value)
			.note("invalid update")
			.owner(ownerName)
			.inAccount(inAccountDescription)
			.outAccount(outAccountDescription)
			.date(date)
			.build();

		// verify update operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient().update(id, entryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		BigDecimal value = new BigDecimal(100);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner("grandmother").name();
		String salary = category(CategoryConstants.SALARY).description();
		String outAccountDescription = creditAccount(CreditAccountConstants.INTERNSHIP, salary).getDescription();
		String cash = category(CategoryConstants.CASH).description();
		String inAccountDescription = equityAccount(EquityAccountConstants.SAVINGS, cash).getDescription();

		// empty id
		updateInvalid(null, null, null, "", "", "", HttpStatus.FORBIDDEN);
		updateInvalid(null, value, date, ownerName, inAccountDescription, outAccountDescription, HttpStatus.FORBIDDEN);

		EntryInsertUpdateDTO entryInsertUpdateDTO = EntryInsertUpdateDTO.builder()
			.value(value)
			.note("valid entry")
			.owner(ownerName)
			.inAccount(inAccountDescription)
			.outAccount(outAccountDescription)
			.date(date)
			.build();
		Long id = entryRestClient().insert(entryInsertUpdateDTO).getId();

		// empty fields
		updateInvalid(id, null, null, "", "", "", HttpStatus.BAD_REQUEST);
		updateInvalid(id, null, date, ownerName, inAccountDescription, outAccountDescription, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, null, ownerName, inAccountDescription, outAccountDescription, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, "", inAccountDescription, outAccountDescription, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, "", outAccountDescription, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, inAccountDescription, "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid(1000000L, value, date, ownerName, inAccountDescription, outAccountDescription,
				HttpStatus.NOT_FOUND);

		// invalid fields
		updateInvalid(id, value, date, "invalid owner", inAccountDescription, outAccountDescription,
				HttpStatus.CONFLICT);
		updateInvalid(id, value, date, ownerName, "invalid in account", outAccountDescription, HttpStatus.CONFLICT);
		updateInvalid(id, value, date, ownerName, inAccountDescription, "invalid out account", HttpStatus.CONFLICT);
	}

	@Test
	void deleteOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		EntryDTO entryDTO = entryRestClient().insert(entryInsertDTO);

		assertThat(entryDTO.getId()).isGreaterThan(0);

		entryRestClient().deleteById(entryDTO.getId());

		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient().findById(entryDTO.getId()))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(long id, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> entryRestClient().deleteById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		long id = 1000;
		deleteInvalid(id, HttpStatus.NOT_FOUND);
	}

}
