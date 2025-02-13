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
		String categoryDescription = category(CategoryConstants.SALARY).getDescription();

		CreditAccountDTO creditAccount = CreditAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		// verify insert operation
		assertThat(creditAccountRestClient().insert(creditAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(creditAccountRestClient().findByDescription(description).get().getDescription())
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
		String categoryDescription = category(CategoryConstants.SALARY).getDescription();

		invalidInsert("", categoryDescription, HttpStatus.BAD_REQUEST);
		invalidInsert(description, "", HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameCreditAccountTwice() {
		String description = "repeated credit account";
		String categoryDescription = category(CategoryConstants.SALARY).getDescription();

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

	@Test
	void updateOne() {
		CreditAccountDTO creditAccount = creditAccount("Inserted creditAccount",
				category(CategoryConstants.SALARY).getDescription());

		String originalDescription = creditAccount.getDescription();
		creditAccount.setDescription("Updated creditAccount");

		creditAccountRestClient().update(originalDescription, creditAccount);

		// verify update operation
		assertThat(creditAccountRestClient().findByDescription(originalDescription)).isEmpty();

		// verify update operation
		assertThat(creditAccountRestClient().findByDescription(creditAccount.getDescription()).get().getDescription())
			.isEqualTo(creditAccount.getDescription());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic creditAccount";

		creditAccountRestClient().insert(CreditAccountDTO.builder()
			.description(description)
			.category(category(CategoryConstants.SALARY).getDescription())
			.build());
		assertThat(creditAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(description);

		// execute deleteByName operation
		creditAccountRestClient().deleteByDescription(description);
		// verify the results
		assertThat(creditAccountRestClient().findByDescription(description)).isEmpty();
	}

}
