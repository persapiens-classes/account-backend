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
class CategoryRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Free income";

		CategoryDTO category = CategoryDTO.builder().description(description).build();

		// verify insert operation
		assertThat(categoryRestClient().insert(category)).isNotNull();

		// verify findByDescription operation
		assertThat(categoryRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(category.getDescription());

		// verify findAll operation
		assertThat(categoryRestClient().findAll()).isNotEmpty();
	}

	private void insertInvalid(String description, HttpStatus httpStatus) {
		CategoryDTO category = CategoryDTO.builder().description(description).build();

		// verify insert operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient().insert(category))
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

		CategoryDTO category = CategoryDTO.builder().description(description).build();

		categoryRestClient().insert(category);

		// verify insert operation
		// verify status code error
		insertInvalid(description, HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		CategoryDTO category = category("Inserted category");

		String originalDescription = category.getDescription();
		category.setDescription("Updated category");

		categoryRestClient().update(originalDescription, category);

		// verify update operation
		assertThat(categoryRestClient().findByDescription(originalDescription)).isEmpty();

		// verify update operation
		assertThat(categoryRestClient().findByDescription(category.getDescription()).get().getDescription())
			.isEqualTo(category.getDescription());
	}

	private void updateInvalid(String oldDescription, String newDescription, HttpStatus httpStatus) {
		CategoryDTO category = CategoryDTO.builder().description(newDescription).build();

		// verify update operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient().update(oldDescription, category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		CategoryDTO categoryToUpdate = category("category to update");

		// empty id
		updateInvalid("", "", HttpStatus.FORBIDDEN);
		updateInvalid("", categoryToUpdate.getDescription(), HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(categoryToUpdate.getDescription(), "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid category", "valid category description", HttpStatus.NOT_FOUND);

		// invalid field
		updateInvalid(categoryToUpdate.getDescription(), categoryToUpdate.getDescription(), HttpStatus.CONFLICT);
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic category";
		categoryRestClient().insert(CategoryDTO.builder().description(description).build());
		assertThat(categoryRestClient().findByDescription(description).get().getDescription()).isEqualTo(description);

		// execute deleteByName operation
		categoryRestClient().deleteByDescription(description);
		// verify the results
		assertThat(categoryRestClient().findByDescription(description)).isEmpty();
	}

	private void deleteInvalid(String description, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> categoryRestClient().deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid category";
		deleteInvalid("", HttpStatus.FORBIDDEN);
		deleteInvalid(description, HttpStatus.NOT_FOUND);
	}

}
