package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.DebitCategory;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DebitCategoryRepositoryIT {

	@Autowired
	private DebitCategoryRepository categoryRepository;

	@Autowired
	private DebitCategoryFactory categoryFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.categoryRepository).isNotNull();
	}

	@Test
	void deleteOne() {
		// create test environment
		DebitCategory category = this.categoryFactory.category("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.categoryRepository.delete(category);

		// verify the results
		assertThat(this.categoryRepository.findById(category.getId())).isNotPresent();
	}

	@Test
	void saveOne() {
		// execute the operation to be tested
		DebitCategory category = this.categoryFactory.transport();

		// verify the results
		assertThat(category.getId()).isNotNull();
	}

}
