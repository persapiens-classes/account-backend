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
		String description = "Free income";

		CategoryDTO category = new CategoryDTO(description);

		var categoryRestClient = debitCategoryRestClient();

		// verify insert operation
		assertThat(categoryRestClient.insert(category)).isNotNull();

		// verify findByDescription operation
		assertThat(categoryRestClient.findByDescription(description).description()).isEqualTo(category.description());

		// verify findAll operation
		assertThat(categoryRestClient.findAll()).isNotEmpty();
	}

	private void insertInvalid(String description, HttpStatus httpStatus) {
		CategoryDTO category = new CategoryDTO(description);

		// verify insert operation
		// verify status code error
		var categoryRestClient = debitCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> categoryRestClient.insert(category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		String description = "";

		insertInvalid(description, HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameCategoryTwice() {
		String description = "repeated category";

		CategoryDTO category = new CategoryDTO(description);

		var categoryRestClient = debitCategoryRestClient();
		categoryRestClient.insert(category);

		// verify insert operation
		// verify status code error
		insertInvalid(description, HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		CategoryDTO category = debitCategory("Inserted category");

		String originalDescription = category.description();
		category = new CategoryDTO("Updated category");

		var categoryRestClient = debitCategoryRestClient();
		category = categoryRestClient.update(originalDescription, category);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient.findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(categoryRestClient.findByDescription(category.description()).description())
			.isEqualTo(category.description());
	}

	private void updateInvalid(String oldDescription, String newDescription, HttpStatus httpStatus) {
		CategoryDTO category = new CategoryDTO(newDescription);

		// verify update operation
		// verify status code error
		var categoryRestClient = debitCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient.update(oldDescription, category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		CategoryDTO categoryToUpdate = debitCategory("category to update");

		// empty id
		updateInvalid("", "", HttpStatus.FORBIDDEN);
		updateInvalid("", categoryToUpdate.description(), HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(categoryToUpdate.description(), "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid category", "valid category description", HttpStatus.NOT_FOUND);

		// invalid field
		updateInvalid(categoryToUpdate.description(), categoryToUpdate.description(), HttpStatus.CONFLICT);
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic category";
		var categoryRestClient = debitCategoryRestClient();
		categoryRestClient.insert(new CategoryDTO(description));
		assertThat(categoryRestClient.findByDescription(description).description()).isEqualTo(description);

		// execute deleteByDescription operation
		categoryRestClient.deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient.findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalidCategory(String description, HttpStatus httpStatus) {
		var categoryRestClient = debitCategoryRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient.deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid category";
		deleteInvalidCategory("", HttpStatus.FORBIDDEN);
		deleteInvalidCategory(description, HttpStatus.NOT_FOUND);
	}

}
