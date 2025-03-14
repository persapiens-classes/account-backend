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
class CreditCategoryRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Free income";

		CategoryDTO category = new CategoryDTO(description);

		var creditCategoryRestClient = creditCategoryRestClient();

		// verify insert operation
		assertThat(creditCategoryRestClient.insert(category)).isNotNull();

		// verify findByDescription operation
		assertThat(creditCategoryRestClient.findByDescription(description).description())
			.isEqualTo(category.description());

		// verify findAll operation
		assertThat(creditCategoryRestClient.findAll()).isNotEmpty();
	}

	private void insertInvalid(String description, HttpStatus httpStatus) {
		CategoryDTO category = new CategoryDTO(description);

		// verify insert operation
		// verify status code error
		var creditCategoryRestClient = creditCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditCategoryRestClient.insert(category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		String description = "";

		insertInvalid(description, HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameCategoryTwice() {
		String description = "repeated credit category";

		CategoryDTO category = new CategoryDTO(description);

		var creditCategoryRestClient = creditCategoryRestClient();
		creditCategoryRestClient.insert(category);

		// verify insert operation
		// verify status code error
		insertInvalid(description, HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		CategoryDTO category = creditCategory("Inserted category");

		String originalDescription = category.description();
		category = new CategoryDTO("Updated category");

		var creditCategoryRestClient = creditCategoryRestClient();
		category = creditCategoryRestClient.update(originalDescription, category);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditCategoryRestClient.findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(creditCategoryRestClient.findByDescription(category.description()).description())
			.isEqualTo(category.description());
	}

	private void updateInvalid(String oldDescription, String newDescription, HttpStatus httpStatus) {
		CategoryDTO category = new CategoryDTO(newDescription);

		// verify update operation
		// verify status code error
		var creditCategoryRestClient = creditCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditCategoryRestClient.update(oldDescription, category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		CategoryDTO creditCategoryToUpdate = creditCategory("category to update");

		// empty id
		updateInvalid("", "", HttpStatus.FORBIDDEN);
		updateInvalid("", creditCategoryToUpdate.description(), HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(creditCategoryToUpdate.description(), "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid category", "valid category description", HttpStatus.NOT_FOUND);

		// invalid field
		updateInvalid(creditCategoryToUpdate.description(), creditCategoryToUpdate.description(), HttpStatus.CONFLICT);
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic category";
		var creditCategoryRestClient = creditCategoryRestClient();
		creditCategoryRestClient.insert(new CategoryDTO(description));
		assertThat(creditCategoryRestClient.findByDescription(description).description()).isEqualTo(description);

		// execute deleteByDescription operation
		creditCategoryRestClient.deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditCategoryRestClient.findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalidCategory(String description, HttpStatus httpStatus) {
		var creditCategoryRestClient = creditCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> creditCategoryRestClient.deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid category";
		deleteInvalidCategory("", HttpStatus.FORBIDDEN);
		deleteInvalidCategory(description, HttpStatus.NOT_FOUND);
	}

}
