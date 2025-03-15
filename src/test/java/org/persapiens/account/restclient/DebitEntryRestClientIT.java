package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.DebitCategoryConstants;
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
class DebitEntryRestClientIT extends RestClientIT {

	private EntryInsertUpdateDTO entry() {
		String mother = owner(OwnerConstants.MOTHER).name();
		String transport = debitCategory(DebitCategoryConstants.TRANSPORT).description();
		String bus = debitAccount(DebitAccountConstants.BUS, transport).description();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		return new EntryInsertUpdateDTO(mother, mother, LocalDateTime.now(), bus, savings, new BigDecimal(543),
				"saving the internship");
	}

	@Test
	void insertOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var debitEntryRestClient = debitEntryRestClient();
		// verify insert operation
		assertThat(debitEntryRestClient.insert(entryInsertDTO)).isNotNull();

		// verify findAll operation
		assertThat(debitEntryRestClient.findAll()).isNotEmpty();
	}

	private void invalidInsert(BigDecimal value, LocalDateTime date, String ownerName, String inAccountDescription,
			String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO debitEntryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				inAccountDescription, outAccountDescription, value, "invalid debit insert");

		// verify insert operation
		// verify status code error
		var debitEntryRestClient = debitEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitEntryRestClient.insert(debitEntryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		BigDecimal value = new BigDecimal(100);
		LocalDateTime date = LocalDateTime.now();
		String mother = owner(OwnerConstants.MOTHER).name();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();
		String transport = debitCategory(DebitCategoryConstants.TRANSPORT).description();
		String gasoline = debitAccount(DebitAccountConstants.GASOLINE, transport).description();

		// test blank fields
		invalidInsert(null, date, mother, savings, gasoline, HttpStatus.BAD_REQUEST);
		invalidInsert(value, null, mother, savings, gasoline, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, "", savings, gasoline, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, mother, "", gasoline, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, mother, savings, "", HttpStatus.BAD_REQUEST);

		// test fields
		invalidInsert(value, date, "invalid owner", savings, gasoline, HttpStatus.NOT_FOUND);
		invalidInsert(value, date, mother, "invalid in account", gasoline, HttpStatus.NOT_FOUND);
		invalidInsert(value, date, mother, savings, "invalid out account", HttpStatus.NOT_FOUND);
	}

	@Test
	void updateOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var debitEntryRestClient = debitEntryRestClient();
		EntryDTO debitEntryDTO = debitEntryRestClient.insert(entryInsertDTO);

		EntryInsertUpdateDTO entryUpdate = new EntryInsertUpdateDTO(debitEntryDTO.inOwner(), debitEntryDTO.outOwner(),
				debitEntryDTO.date(), debitEntryDTO.inAccount().description(), debitEntryDTO.outAccount().description(),
				debitEntryDTO.value(), "updated note");

		debitEntryDTO = debitEntryRestClient.update(debitEntryDTO.id(), entryUpdate);

		assertThat(debitEntryRestClient.findById(debitEntryDTO.id()).note()).isEqualTo("updated note");
	}

	private void updateInvalid(Long id, BigDecimal value, LocalDateTime date, String ownerName,
			String inAccountDescription, String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO debitEntryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				inAccountDescription, outAccountDescription, value, "invalid update");

		// verify update operation
		// verify status code error
		var debitEntryRestClient = debitEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitEntryRestClient.update(id, debitEntryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		BigDecimal value = new BigDecimal(100);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner("grandmother").name();
		String transport = debitCategory(DebitCategoryConstants.TRANSPORT).description();
		String gasoline = debitAccount(DebitAccountConstants.GASOLINE, transport).description();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String investment = equityAccount(EquityAccountConstants.INVESTMENT, bank).description();

		// empty id
		updateInvalid(null, null, null, "", "", "", HttpStatus.FORBIDDEN);
		updateInvalid(null, value, date, ownerName, gasoline, investment, HttpStatus.FORBIDDEN);

		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				gasoline, investment, value, "valid entry");
		var entryRestClient = debitEntryRestClient();
		Long id = entryRestClient.insert(entryInsertUpdateDTO).id();

		// empty fields
		updateInvalid(id, null, null, "", "", "", HttpStatus.BAD_REQUEST);
		updateInvalid(id, null, date, ownerName, gasoline, investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, null, ownerName, gasoline, investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, "", gasoline, investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, "", investment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, gasoline, "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid(1000000L, value, date, ownerName, gasoline, investment,
				HttpStatus.NOT_FOUND);

		// invalid fields
		updateInvalid(id, value, date, "invalid owner", gasoline, investment,
				HttpStatus.NOT_FOUND);
		updateInvalid(id, value, date, ownerName, "invalid in account", investment, HttpStatus.NOT_FOUND);
		updateInvalid(id, value, date, ownerName, gasoline, "invalid out account", HttpStatus.NOT_FOUND);
	}

	@Test
	void deleteOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		EntryRestClient debitEntryRestClient = debitEntryRestClient();
		EntryDTO inserted = debitEntryRestClient.insert(entryInsertDTO);

		long id = inserted.id();

		assertThat(id).isGreaterThan(0);

		debitEntryRestClient.deleteById(id);

		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> debitEntryRestClient.findById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(long id, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var debitEntryRestClient = debitEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> debitEntryRestClient.deleteById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		long id = 1000;
		deleteInvalid(id, HttpStatus.NOT_FOUND);
	}

}
