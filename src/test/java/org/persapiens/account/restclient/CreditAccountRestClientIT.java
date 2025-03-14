package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.dto.CreditAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditAccountRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "New job";
		String categoryDescription = category(EquityCategoryConstants.SALARY).description();

		CreditAccountDTO creditAccount = new CreditAccountDTO(description, categoryDescription);

		var creditAccountRestClient = creditAccountRestClient();

		// verify insert operation
		assertThat(creditAccountRestClient.insert(creditAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(creditAccountRestClient.findByDescription(description).description())
			.isEqualTo(creditAccount.description());

		// verify findAll operation
		assertThat(creditAccountRestClient.findAll()).isNotEmpty();
	}

	private void invalidInsert(String description, String categoryDescription, HttpStatus httpStatus) {
		CreditAccountDTO creditAccountDto = new CreditAccountDTO(description, categoryDescription);

		// verify insert operation
		// verify status code error
		var creditAccountRestClient = creditAccountRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient.insert(creditAccountDto))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		String description = "not empty";
		String categoryDescription = category(EquityCategoryConstants.SALARY).description();

		invalidInsert("", categoryDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(description, "", HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameCreditAccountTwice() {
		String description = "repeated credit account";
		String categoryDescription = category(EquityCategoryConstants.SALARY).description();

		CreditAccountDTO creditAccountDto = new CreditAccountDTO(description, categoryDescription);

		var creditAccountRestClient = creditAccountRestClient();
		creditAccountRestClient.insert(creditAccountDto);

		// verify insert operation
		// verify status code error
		invalidInsert(description, categoryDescription, HttpStatus.CONFLICT);
	}

	@Test
	void insertCategoryNotInserted() {
		String description = "repeated credit account";
		String categoryDescription = "category not inserted";

		invalidInsert(description, categoryDescription, HttpStatus.CONFLICT);
	}

	private void updateInvalid(String oldDescription, String description, String categoryDescription,
			HttpStatus httpStatus) {
		CreditAccountDTO creditAccount = new CreditAccountDTO(description, categoryDescription);

		// verify update operation
		// verify status code error
		var creditAccountRestClient = creditAccountRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient.update(oldDescription, creditAccount))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		String categoryDescription = category(EquityCategoryConstants.SALARY).description();
		CreditAccountDTO creditAccountToUpdate = creditAccount("credit account to update", categoryDescription);

		// empty id
		updateInvalid("", "", "", HttpStatus.FORBIDDEN);
		updateInvalid("", "", creditAccountToUpdate.category(), HttpStatus.FORBIDDEN);
		updateInvalid("", creditAccountToUpdate.description(), "", HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(creditAccountToUpdate.description(), "", "", HttpStatus.BAD_REQUEST);
		updateInvalid(creditAccountToUpdate.description(), "credit account updated", "", HttpStatus.BAD_REQUEST);
		updateInvalid(creditAccountToUpdate.description(), "", creditAccountToUpdate.category(),
				HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid credit account", "credit account updated", creditAccountToUpdate.category(),
				HttpStatus.NOT_FOUND);

		// invalid fields
		updateInvalid(creditAccountToUpdate.description(), "credit account updated", "invalid category",
				HttpStatus.NOT_FOUND);
		updateInvalid(creditAccountToUpdate.description(), creditAccountToUpdate.description(),
				creditAccountToUpdate.category(), HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		CreditAccountDTO creditAccount = creditAccount("Inserted creditAccount",
				category(EquityCategoryConstants.SALARY).description());

		String originalDescription = creditAccount.description();
		creditAccount = new CreditAccountDTO("Updated creditAccount", creditAccount.category());

		var creditAccountRestClient = creditAccountRestClient();
		creditAccount = creditAccountRestClient.update(originalDescription, creditAccount);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient.findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(creditAccountRestClient.findByDescription(creditAccount.description()).description())
			.isEqualTo(creditAccount.description());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic creditAccount";

		var creditAccountRestClient = creditAccountRestClient();
		creditAccountRestClient
			.insert(new CreditAccountDTO(description, category(EquityCategoryConstants.SALARY).description()));
		assertThat(creditAccountRestClient.findByDescription(description).description()).isEqualTo(description);

		// execute deleteByDescription operation
		creditAccountRestClient.deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient.findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalidCreditAccount(String description, HttpStatus httpStatus) {
		var creditAccountRestClient = creditAccountRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient.deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid credit account";
		deleteInvalidCreditAccount("", HttpStatus.FORBIDDEN);
		deleteInvalidCreditAccount(description, HttpStatus.NOT_FOUND);
	}

}
