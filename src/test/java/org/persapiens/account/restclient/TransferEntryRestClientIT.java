package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
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
class TransferEntryRestClientIT extends RestClientIT {

	private EntryInsertUpdateDTO entry() {
		String mother = owner(OwnerConstants.MOTHER).name();
		String cash = equityCategory(EquityCategoryConstants.CASH).description();
		String wallet = equityAccount(EquityAccountConstants.WALLET, cash).description();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		return new EntryInsertUpdateDTO(mother, mother, LocalDateTime.now(), wallet, savings, new BigDecimal(543),
				"saving the internship");
	}

	@Test
	void insertOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var entryRestClient = transferEntryRestClient();
		// verify insert operation
		assertThat(entryRestClient.insert(entryInsertDTO)).isNotNull();

		// verify findAll operation
		assertThat(entryRestClient.findAll()).isNotEmpty();
	}

	private void invalidInsert(BigDecimal value, LocalDateTime date, String ownerName, String inAccountDescription,
			String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				inAccountDescription, outAccountDescription, value, "invalid insert");

		// verify insert operation
		// verify status code error
		var entryRestClient = transferEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient.insert(entryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		BigDecimal value = new BigDecimal(102);
		LocalDateTime date = LocalDateTime.now();
		String mother = owner(OwnerConstants.MOTHER).name();
		String bank = creditCategory(EquityCategoryConstants.BANK).description();
		String checking = creditAccount(EquityAccountConstants.CHECKING, bank).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		// test blank fields
		invalidInsert(null, date, mother, savings, checking, HttpStatus.BAD_REQUEST);
		invalidInsert(value, null, mother, savings, checking, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, "", savings, checking, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, mother, "", checking, HttpStatus.BAD_REQUEST);
		invalidInsert(value, date, mother, savings, "", HttpStatus.BAD_REQUEST);

		// test fields
		invalidInsert(value, date, "invalid owner", savings, checking, HttpStatus.NOT_FOUND);
		invalidInsert(value, date, mother, "invalid in account", checking, HttpStatus.NOT_FOUND);
		invalidInsert(value, date, mother, savings, "invalid out account", HttpStatus.NOT_FOUND);
	}

	@Test
	void updateOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		var entryRestClient = transferEntryRestClient();
		EntryDTO entryDTO = entryRestClient.insert(entryInsertDTO);

		EntryInsertUpdateDTO entryUpdate = new EntryInsertUpdateDTO(entryDTO.inOwner(), entryDTO.outOwner(),
				entryDTO.date(), entryDTO.inAccount().description(), entryDTO.outAccount().description(),
				entryDTO.value(), "updated note");

		entryDTO = entryRestClient.update(entryDTO.id(), entryUpdate);

		assertThat(entryRestClient.findById(entryDTO.id()).note()).isEqualTo("updated note");
	}

	private void updateInvalid(Long id, BigDecimal value, LocalDateTime date, String ownerName,
			String inAccountDescription, String outAccountDescription, HttpStatus httpStatus) {
		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				inAccountDescription, outAccountDescription, value, "invalid update");

		// verify update operation
		// verify status code error
		var entryRestClient = transferEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> entryRestClient.update(id, entryInsertUpdateDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		BigDecimal value = new BigDecimal(102);
		LocalDateTime date = LocalDateTime.now();
		String ownerName = owner("grandmother").name();
		String cash = equityCategory(EquityCategoryConstants.CASH).description();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, cash).description();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String investiment = equityAccount(EquityAccountConstants.INVESTMENT, bank).description();

		// empty id
		updateInvalid(null, null, null, "", "", "", HttpStatus.FORBIDDEN);
		updateInvalid(null, value, date, ownerName, savings, investiment, HttpStatus.FORBIDDEN);

		EntryInsertUpdateDTO entryInsertUpdateDTO = new EntryInsertUpdateDTO(ownerName, ownerName, date,
				savings, investiment, value, "valid entry");
		var entryRestClient = transferEntryRestClient();
		Long id = entryRestClient.insert(entryInsertUpdateDTO).id();

		// empty fields
		updateInvalid(id, null, null, "", "", "", HttpStatus.BAD_REQUEST);
		updateInvalid(id, null, date, ownerName, savings, investiment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, null, ownerName, savings, investiment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, "", savings, investiment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, "", investiment, HttpStatus.BAD_REQUEST);
		updateInvalid(id, value, date, ownerName, savings, "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid(1000000L, value, date, ownerName, savings, investiment,
				HttpStatus.NOT_FOUND);

		// invalid fields
		updateInvalid(id, value, date, "invalid owner", savings, investiment,
				HttpStatus.NOT_FOUND);
		updateInvalid(id, value, date, ownerName, "invalid in account", investiment, HttpStatus.NOT_FOUND);
		updateInvalid(id, value, date, ownerName, savings, "invalid out account", HttpStatus.NOT_FOUND);
	}

	@Test
	void deleteOne() {
		EntryInsertUpdateDTO entryInsertDTO = entry();

		EntryRestClient entryRestClient = transferEntryRestClient();
		EntryDTO inserted = entryRestClient.insert(entryInsertDTO);

		long id = inserted.id();

		assertThat(id).isGreaterThan(0);

		entryRestClient.deleteById(id);

		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> entryRestClient.findById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(long id, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var entryRestClient = transferEntryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> entryRestClient.deleteById(id))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		long id = 1000;
		deleteInvalid(id, HttpStatus.NOT_FOUND);
	}

}
