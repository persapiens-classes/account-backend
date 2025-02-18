package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
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
		String categoryDescription = category(CategoryConstants.SALARY).description();

		CreditAccountDTO creditAccount = CreditAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		// verify insert operation
		assertThat(creditAccountRestClient().insert(creditAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(creditAccountRestClient().findByDescription(description).getDescription())
			.isEqualTo(creditAccount.getDescription());

		// verify findAll operation
		assertThat(creditAccountRestClient().findAll()).isNotEmpty();
	}

	private void invalidInsert(String description, String categoryDescription, HttpStatus httpStatus) {
		CreditAccountDTO creditAccountDto = CreditAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		// verify insert operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient().insert(creditAccountDto))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		String description = "not empty";
		String categoryDescription = category(CategoryConstants.SALARY).description();

		invalidInsert("", categoryDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(description, "", HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameCreditAccountTwice() {
		String description = "repeated credit account";
		String categoryDescription = category(CategoryConstants.SALARY).description();

		CreditAccountDTO creditAccountDto = CreditAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		creditAccountRestClient().insert(creditAccountDto);

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
		CreditAccountDTO creditAccount = CreditAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		// verify update operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient().update(oldDescription, creditAccount))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		String categoryDescription = category(CategoryConstants.SALARY).description();
		CreditAccountDTO creditAccountToUpdate = creditAccount("credit account to update", categoryDescription);

		// empty id
		updateInvalid("", "", "", HttpStatus.FORBIDDEN);
		updateInvalid("", "", creditAccountToUpdate.getCategory(), HttpStatus.FORBIDDEN);
		updateInvalid("", creditAccountToUpdate.getDescription(), "", HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(creditAccountToUpdate.getDescription(), "", "", HttpStatus.BAD_REQUEST);
		updateInvalid(creditAccountToUpdate.getDescription(), "credit account updated", "", HttpStatus.BAD_REQUEST);
		updateInvalid(creditAccountToUpdate.getDescription(), "", creditAccountToUpdate.getCategory(),
				HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid credit account", "credit account updated", creditAccountToUpdate.getCategory(),
				HttpStatus.NOT_FOUND);

		// invalid fields
		updateInvalid(creditAccountToUpdate.getDescription(), "credit account updated", "invalid category",
				HttpStatus.CONFLICT);
		updateInvalid(creditAccountToUpdate.getDescription(), creditAccountToUpdate.getDescription(),
				creditAccountToUpdate.getCategory(), HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		CreditAccountDTO creditAccount = creditAccount("Inserted creditAccount",
				category(CategoryConstants.SALARY).description());

		String originalDescription = creditAccount.getDescription();
		creditAccount.setDescription("Updated creditAccount");

		creditAccountRestClient().update(originalDescription, creditAccount);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient().findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(creditAccountRestClient().findByDescription(creditAccount.getDescription()).getDescription())
			.isEqualTo(creditAccount.getDescription());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic creditAccount";

		creditAccountRestClient().insert(CreditAccountDTO.builder()
			.description(description)
			.category(category(CategoryConstants.SALARY).description())
			.build());
		assertThat(creditAccountRestClient().findByDescription(description).getDescription()).isEqualTo(description);

		// execute deleteByDescription operation
		creditAccountRestClient().deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient().findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalidCreditAccount(String description, HttpStatus httpStatus) {
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditAccountRestClient().deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid credit account";
		deleteInvalidCreditAccount("", HttpStatus.FORBIDDEN);
		deleteInvalidCreditAccount(description, HttpStatus.NOT_FOUND);
	}

}
