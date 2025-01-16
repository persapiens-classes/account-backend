package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CategoryDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
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

	@Test
	void updateOne() {
		CategoryDTO category = category("Inserted category");

		String originalDescription = category.getDescription();
		category.setDescription("Updated category");

		categoryRestClient().update(originalDescription, category);

		// verify update operation
		assertThat(categoryRestClient().findByDescription(originalDescription)).isEmpty();

		// verify update operation
		assertThat(categoryRestClient().findByDescription(category.getDescription()).get().getDescription()).isEqualTo(category.getDescription());
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

}
