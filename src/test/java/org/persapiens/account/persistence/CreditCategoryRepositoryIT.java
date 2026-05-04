package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.CreditCategory;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CreditCategoryRepositoryIT {

	@Autowired
	private CreditCategoryRepository categoryRepository;

	@Autowired
	private CreditCategoryFactory categoryFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.categoryRepository).isNotNull();
	}

	@Test
	void deleteOne() {
		// create test environment
		CreditCategory category = this.categoryFactory.category("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.categoryRepository.delete(category);

		// verify the results
		assertThat(this.categoryRepository.findById(category.getId())).isNotPresent();
	}

	@Test
	void saveOne() {
		// execute the operation to be tested
		CreditCategory category = this.categoryFactory.salary();

		// verify the results
		assertThat(category.getId()).isNotNull();
	}

}
