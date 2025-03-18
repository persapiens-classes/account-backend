package org.persapiens.account.restclient;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerEquityAccountInitialValueRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		OwnerDTO mother = owner(OwnerConstants.MOTHER);
		AccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				equityCategory(EquityCategoryConstants.BANK).description());

		OwnerEquityAccountInitialValueInsertDTO ownerEquityAccountInitialValueInsert = new OwnerEquityAccountInitialValueInsertDTO(
				mother.name(), savings.description(), new BigDecimal(1000).setScale(2));

		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		// run insert operation
		OwnerEquityAccountInitialValueDTO inserted = ownerEquityAccountInitialValueRestClient
			.insert(ownerEquityAccountInitialValueInsert);

		// verify findAll operation
		assertThat(inserted).isEqualTo(ownerEquityAccountInitialValueRestClient
			.findByOwnerAndEquityAccount(mother.name(), savings.description()));
		assertThat(ownerEquityAccountInitialValueRestClient.findAll()).isNotEmpty();
	}

	private void insertInvalid(BigDecimal value, String owner, String equityAccount, HttpStatus httpStatus) {
		OwnerEquityAccountInitialValueInsertDTO ownerEquityAccountInitialValueInsertDto = new OwnerEquityAccountInitialValueInsertDTO(
				owner, equityAccount, value);

		// verify insert operation
		// verify status code error
		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerEquityAccountInitialValueRestClient.insert(ownerEquityAccountInitialValueInsertDto))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		OwnerDTO mother = owner(OwnerConstants.MOTHER);
		AccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				equityCategory(EquityCategoryConstants.BANK).description());

		// null and empty
		insertInvalid(null, mother.name(), savings.description(), HttpStatus.BAD_REQUEST);
		insertInvalid(new BigDecimal(100), "", savings.description(), HttpStatus.BAD_REQUEST);
		insertInvalid(new BigDecimal(100), mother.name(), "", HttpStatus.BAD_REQUEST);

		// invalid owner and equity account
		insertInvalid(new BigDecimal(100), "invalid owner", savings.description(), HttpStatus.NOT_FOUND);
		insertInvalid(new BigDecimal(100), mother.name(), "invalid equity account", HttpStatus.NOT_FOUND);
	}

	@Test
	void insertSameOwnerEquityAccountInitialValueTwice() {
		OwnerDTO uncle = owner(OwnerConstants.UNCLE);
		AccountDTO savings = equityAccount(EquityAccountConstants.INVESTMENT,
				equityCategory(EquityCategoryConstants.BANK).description());

		OwnerEquityAccountInitialValueInsertDTO ownerEquityAccountInitialValueInsert = new OwnerEquityAccountInitialValueInsertDTO(
				uncle.name(), savings.description(), new BigDecimal(1000));

		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		ownerEquityAccountInitialValueRestClient.insert(ownerEquityAccountInitialValueInsert);

		// verify insert operation
		// verify status code error
		insertInvalid(new BigDecimal(1000), uncle.name(), savings.description(), HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		AccountDTO individualAssets = equityAccount(EquityAccountConstants.INDIVIDUAL_ASSETS,
				equityCategory(EquityCategoryConstants.BANK).description());

		OwnerEquityAccountInitialValueInsertDTO ownerEquityAccountInitialValue = new OwnerEquityAccountInitialValueInsertDTO(
				aunt.name(), individualAssets.description(), new BigDecimal(1000));

		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		OwnerEquityAccountInitialValueDTO inserted = ownerEquityAccountInitialValueRestClient
			.insert(ownerEquityAccountInitialValue);

		OwnerEquityAccountInitialValueDTO updated = ownerEquityAccountInitialValueRestClient.update(inserted.owner(),
				inserted.equityAccount().description(), new BigDecimal(2000));

		assertThat(updated.value()).isEqualTo(new BigDecimal(2000));
		assertThat(ownerEquityAccountInitialValueRestClient.findAll()).isNotEmpty();
	}

	private void updateInvalid(String owner, String equityAccount, BigDecimal value, HttpStatus httpStatus) {
		// verify update operation
		// verify status code error
		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerEquityAccountInitialValueRestClient.update(owner, equityAccount, value))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		OwnerDTO grandmother = owner("grandmother");
		AccountDTO savings = equityAccount(EquityAccountConstants.INVESTMENT,
				equityCategory(EquityCategoryConstants.BANK).description());

		// empty id
		updateInvalid("", "", new BigDecimal(100), HttpStatus.BAD_REQUEST);
		updateInvalid(grandmother.name(), "", new BigDecimal(100), HttpStatus.BAD_REQUEST);
		updateInvalid("", savings.description(), new BigDecimal(100), HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid owner", savings.description(), new BigDecimal(100), HttpStatus.NOT_FOUND);
		updateInvalid(grandmother.name(), "invalid equity account", new BigDecimal(100), HttpStatus.NOT_FOUND);
		updateInvalid(grandmother.name(), savings.description(), new BigDecimal(100), HttpStatus.NOT_FOUND);
	}

	@Test
	void deleteOne() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		AccountDTO investment = equityAccount(EquityAccountConstants.INVESTMENT,
				equityCategory(EquityCategoryConstants.BANK).description());

		OwnerEquityAccountInitialValueInsertDTO ownerEquityAccountInitialValue = new OwnerEquityAccountInitialValueInsertDTO(
				aunt.name(), investment.description(), new BigDecimal(1000));

		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		OwnerEquityAccountInitialValueDTO inserted = ownerEquityAccountInitialValueRestClient
			.insert(ownerEquityAccountInitialValue);
		String owner = inserted.owner();
		String equityAccount = inserted.equityAccount().description();

		ownerEquityAccountInitialValueRestClient.deleteByOwnerAndEquityAccount(owner, equityAccount);

		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(
					() -> ownerEquityAccountInitialValueRestClient.findByOwnerAndEquityAccount(owner, equityAccount))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(String ownerName, String equityAccountDescription, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		var ownerEquityAccountInitialValueRestClient = ownerEquityAccountInitialValueRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerEquityAccountInitialValueRestClient.deleteByOwnerAndEquityAccount(ownerName,
					equityAccountDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String father = owner(OwnerConstants.FATHER).name();
		String savings = equityAccount(EquityAccountConstants.INVESTMENT,
				equityCategory(EquityCategoryConstants.BANK).description())
			.description();

		deleteInvalid("", savings, HttpStatus.BAD_REQUEST);
		deleteInvalid(father, "", HttpStatus.BAD_REQUEST);
		deleteInvalid("invalid owner", savings, HttpStatus.NOT_FOUND);
		deleteInvalid(father, "invalid equity account", HttpStatus.NOT_FOUND);
		deleteInvalid(father, savings, HttpStatus.NOT_FOUND);
	}

}
