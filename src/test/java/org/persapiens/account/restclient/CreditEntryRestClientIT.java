package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.CreditCategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditEntryRestClientIT extends RestClientIT {

	private EntryInsertUpdateDTO entry() {
		String mother = owner(OwnerConstants.MOTHER).name();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();
		String salary = creditCategory(CreditCategoryConstants.SALARY).description();
		String work = creditAccount(CreditAccountConstants.WORK, salary).description();

		return new EntryInsertUpdateDTO(mother, mother, LocalDateTime.now(), savings, work, new BigDecimal(543),
				"saving the internship");
	}

	@Test
	void insertOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var creditEntryRestClient = creditEntryRestClient();
		// verify insert operation
		assertThat(creditEntryRestClient.insert(entryInsertDTO)).isNotNull();

		// verify findAll operation
		assertThat(creditEntryRestClient.findAll()).isNotEmpty();
	}

	private void invalidInsert(BigDecimal value, LocalDateTime date, String ownerName, String inAccountDescription,
			String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO creditEntryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				inAccountDescription, outAccountDescription, value, "invalid credit insert");

		// verify insert operation
		// verify status code error
		var creditEntryRestClient = creditEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditEntryRestClient.insert(creditEntryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		BigDecimal value = new BigDecimal(101);
		LocalDateTime date = LocalDateTime.now();
		String mother = owner(OwnerConstants.MOTHER).name();
		String salary = creditCategory(CreditCategoryConstants.SALARY).description();
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP, salary).description();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		// test blank fields
		invalidInsert(null, date, mother, savings, internship, HttpStatus.BAD_REQUEST);
		invalidInsert(value, null, mother, savings, internship, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, "", savings, internship, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, mother, "", internship, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, mother, savings, "", HttpStatus.BAD_REQUEST);

		// test fields
		invalidInsert(value, date, "invalid owner", savings, internship, HttpStatus.NOT_FOUND);
		invalidInsert(value, date, mother, "invalid in account", internship, HttpStatus.NOT_FOUND);
		invalidInsert(value, date, mother, savings, "invalid out account", HttpStatus.NOT_FOUND);
	}

	@Test
	void updateOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var creditEntryRestClient = creditEntryRestClient();
		EntryDTO creditEntryDTO = creditEntryRestClient.insert(entryInsertDTO);

		EntryInsertUpdateDTO entryUpdate = new EntryInsertUpdateDTO(creditEntryDTO.inOwner(), creditEntryDTO.outOwner(),
				creditEntryDTO.date(), creditEntryDTO.inAccount().description(), creditEntryDTO.outAccount().description(),
				creditEntryDTO.value(), "updated note");

		creditEntryDTO = creditEntryRestClient.update(creditEntryDTO.id(), entryUpdate);

		assertThat(creditEntryRestClient.findById(creditEntryDTO.id()).note()).isEqualTo("updated note");
	}

	private void updateInvalid(Long id, BigDecimal value, LocalDateTime date, String ownerName,
			String inAccountDescription, String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO creditEntryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				inAccountDescription, outAccountDescription, value, "invalid update");

		// verify update operation
		// verify status code error
		var creditEntryRestClient = creditEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditEntryRestClient.update(id, creditEntryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		BigDecimal value = new BigDecimal(101);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner("grandmother").name();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String investment = equityAccount(EquityAccountConstants.INVESTMENT, bank).description();
		String interim = creditCategory(CreditCategoryConstants.INTERIM).description();
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP, interim).description();

		// empty id
		updateInvalid(null, null, null, "", "", "", HttpStatus.FORBIDDEN);
		updateInvalid(null, value, date, ownerName, internship, investment, HttpStatus.FORBIDDEN);

		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				investment, internship, value, "valid entry");
		var entryRestClient = creditEntryRestClient();
		Long id = entryRestClient.insert(entryInsertUpdateDTO).id();

		// empty fields
		updateInvalid(id, null, null, "", "", "", HttpStatus.BAD_REQUEST);
		updateInvalid(id, null, date, ownerName, internship, investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, null, ownerName, internship, investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, "", internship, investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, "", investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, internship, "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid(1000000L, value, date, ownerName, internship, investment,
				HttpStatus.NOT_FOUND);

		// invalid fields
		updateInvalid(id, value, date, "invalid owner", internship, investment,
				HttpStatus.NOT_FOUND);
		updateInvalid(id, value, date, ownerName, "invalid in account", investment, HttpStatus.NOT_FOUND);
		updateInvalid(id, value, date, ownerName, internship, "invalid out account", HttpStatus.NOT_FOUND);
	}

	@Test
	void deleteOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		EntryRestClient creditEntryRestClient = creditEntryRestClient();
		EntryDTO inserted = creditEntryRestClient.insert(entryInsertDTO);

		long id = inserted.id();

		assertThat(id).isGreaterThan(0);

		creditEntryRestClient.deleteById(id);

		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> creditEntryRestClient.findById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(long id, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var creditEntryRestClient = creditEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> creditEntryRestClient.deleteById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		long id = 1000;
		deleteInvalid(id, HttpStatus.NOT_FOUND);
	}

}
