package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CategoryDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebitCategoryRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Free debit";

		CategoryDTO category = new CategoryDTO(description);

		var debitCategoryRestClient = debitCategoryRestClient();

		// verify insert operation
		assertThat(debitCategoryRestClient.insert(category)).isNotNull();

		// verify findByDescription operation
		assertThat(debitCategoryRestClient.findByDescription(description).description())
			.isEqualTo(category.description());

		// verify findAll operation
		assertThat(debitCategoryRestClient.findAll()).isNotEmpty();
	}

	private void insertInvalid(String description, HttpStatus httpStatus) {
		CategoryDTO category = new CategoryDTO(description);

		// verify insert operation
		// verify status code error
		var debitCategoryRestClient = debitCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitCategoryRestClient.insert(category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		String description = "";

		insertInvalid(description, HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameCategoryTwice() {
		String description = "repeated debit category";

		CategoryDTO category = new CategoryDTO(description);

		var debitCategoryRestClient = debitCategoryRestClient();
		debitCategoryRestClient.insert(category);

		// verify insert operation
		// verify status code error
		insertInvalid(description, HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		CategoryDTO category = debitCategory("Inserted category");

		String originalDescription = category.description();
		category = new CategoryDTO("Updated category");

		var debitCategoryRestClient = debitCategoryRestClient();
		category = debitCategoryRestClient.update(originalDescription, category);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitCategoryRestClient.findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(debitCategoryRestClient.findByDescription(category.description()).description())
			.isEqualTo(category.description());
	}

	private void updateInvalid(String oldDescription, String newDescription, HttpStatus httpStatus) {
		CategoryDTO category = new CategoryDTO(newDescription);

		// verify update operation
		// verify status code error
		var debitCategoryRestClient = debitCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitCategoryRestClient.update(oldDescription, category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		CategoryDTO debitCategoryToUpdate = debitCategory("category to update");

		// empty id
		updateInvalid("", "", HttpStatus.FORBIDDEN);
		updateInvalid("", debitCategoryToUpdate.description(), HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(debitCategoryToUpdate.description(), "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid category", "valid category description", HttpStatus.NOT_FOUND);

		// invalid field
		updateInvalid(debitCategoryToUpdate.description(), debitCategoryToUpdate.description(), HttpStatus.CONFLICT);
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic category";
		var debitCategoryRestClient = debitCategoryRestClient();
		debitCategoryRestClient.insert(new CategoryDTO(description));
		assertThat(debitCategoryRestClient.findByDescription(description).description()).isEqualTo(description);

		// execute deleteByDescription operation
		debitCategoryRestClient.deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitCategoryRestClient.findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalidCategory(String description, HttpStatus httpStatus) {
		var debitCategoryRestClient = debitCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitCategoryRestClient.deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid category";
		deleteInvalidCategory("", HttpStatus.FORBIDDEN);
		deleteInvalidCategory(description, HttpStatus.NOT_FOUND);
	}

}
