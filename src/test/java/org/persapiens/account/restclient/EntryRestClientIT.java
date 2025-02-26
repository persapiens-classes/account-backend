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

	private static final String INVALID_OWNER = "invalid owner";

	private static final String INVALID_EQUITY_ACCOUNT = "invalid equity account";

	private EntryInsertUpdateDTO entry() {
		String mother = owner(OwnerConstants.MOTHER).name();
		String salary = category(CategoryConstants.SALARY).description();
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP, salary).description();
		String bank = category(CategoryConstants.BANK).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		return new EntryInsertUpdateDTO(mother, LocalDateTime.now(), savings, internship, new BigDecimal(543),
				"saving the internship");
	}

	@Test
	void insertOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var entryRestClient = entryRestClient();
		// verify insert operation
		assertThat(entryRestClient.insert(entryInsertDTO)).isNotNull();

		// verify findAll operation
		assertThat(entryRestClient.findAll()).isNotEmpty();
	}

	private void invalidInsert(BigDecimal value, LocalDateTime date, String ownerName, String inAccountDescription,
			String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, date, inAccountDescription,
				outAccountDescription, value, "invalid insert");

		// verify insert operation
		// verify status code error
		var entryRestClient = entryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient.insert(entryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		BigDecimal value = new BigDecimal(100);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner(OwnerConstants.MOTHER).name();
		String salary = category(CategoryConstants.SALARY).description();
		String outAccountDescription = creditAccount(CreditAccountConstants.INTERNSHIP, salary).description();
		String bank = category(CategoryConstants.BANK).description();
		String inAccountDescription = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

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

		var entryRestClient = entryRestClient();
		EntryDTO entryDTO = entryRestClient.insert(entryInsertDTO);

		EntryInsertUpdateDTO entryUpdate = new EntryInsertUpdateDTO(entryDTO.owner(), entryDTO.date(),
				entryDTO.inAccount().description(), entryDTO.outAccount().description(), entryDTO.value(),
				"updated note");

		entryDTO = entryRestClient.update(entryDTO.id(), entryUpdate);

		assertThat(entryRestClient.findById(entryDTO.id()).note()).isEqualTo("updated note");
	}

	private void updateInvalid(Long id, BigDecimal value, LocalDateTime date, String ownerName,
			String inAccountDescription, String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, date, inAccountDescription,
				outAccountDescription, value, "invalid update");

		// verify update operation
		// verify status code error
		var entryRestClient = entryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient.update(id, entryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		BigDecimal value = new BigDecimal(100);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner("grandmother").name();
		String salary = category(CategoryConstants.SALARY).description();
		String outAccountDescription = creditAccount(CreditAccountConstants.INTERNSHIP, salary).description();
		String cash = category(CategoryConstants.CASH).description();
		String inAccountDescription = equityAccount(EquityAccountConstants.SAVINGS, cash).description();

		// empty id
		updateInvalid(null, null, null, "", "", "", HttpStatus.FORBIDDEN);
		updateInvalid(null, value, date, ownerName, inAccountDescription, outAccountDescription, HttpStatus.FORBIDDEN);

		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, date, inAccountDescription,
				outAccountDescription, value, "valid entry");
		var entryRestClient = entryRestClient();
		Long id = entryRestClient.insert(entryInsertUpdateDTO).id();

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

		var entryRestClient = entryRestClient();
		var id = entryRestClient.insert(entryInsertDTO).id();

		assertThat(id).isGreaterThan(0);

		entryRestClient.deleteById(id);

		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> entryRestClient.findById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(long id, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var entryRestClient = entryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> entryRestClient.deleteById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		long id = 1000;
		deleteInvalid(id, HttpStatus.NOT_FOUND);
	}

	private void creditSumInvalid(String owner, String equityAccount, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var entryRestClient = entryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient.creditSum(owner, equityAccount))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void creditSumInvalid() {
		String mother = owner(OwnerConstants.MOTHER).name();
		String salary = category(CategoryConstants.SALARY).description();

		creditSumInvalid("", "", HttpStatus.BAD_REQUEST);
		creditSumInvalid(INVALID_OWNER, "", HttpStatus.CONFLICT);
		creditSumInvalid("", INVALID_EQUITY_ACCOUNT, HttpStatus.BAD_REQUEST);
		creditSumInvalid(INVALID_OWNER, INVALID_EQUITY_ACCOUNT, HttpStatus.CONFLICT);
		creditSumInvalid(mother, "", HttpStatus.BAD_REQUEST);
		creditSumInvalid(mother, INVALID_EQUITY_ACCOUNT, HttpStatus.CONFLICT);
		creditSumInvalid(INVALID_OWNER, salary, HttpStatus.CONFLICT);
	}

	private void debitSumInvalid(String owner, String equityAccount, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var entryRestClient = entryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient.debitSum(owner, equityAccount))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void debitSumInvalid() {
		String mother = owner(OwnerConstants.MOTHER).name();
		String salary = category(CategoryConstants.SALARY).description();

		debitSumInvalid("", "", HttpStatus.BAD_REQUEST);
		debitSumInvalid(INVALID_OWNER, "", HttpStatus.CONFLICT);
		debitSumInvalid("", INVALID_EQUITY_ACCOUNT, HttpStatus.BAD_REQUEST);
		debitSumInvalid(INVALID_OWNER, INVALID_EQUITY_ACCOUNT, HttpStatus.CONFLICT);
		debitSumInvalid(mother, "", HttpStatus.BAD_REQUEST);
		debitSumInvalid(mother, INVALID_EQUITY_ACCOUNT, HttpStatus.CONFLICT);
		debitSumInvalid(INVALID_OWNER, salary, HttpStatus.CONFLICT);
	}

}
