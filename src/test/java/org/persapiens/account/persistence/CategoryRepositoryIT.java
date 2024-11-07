package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryRepositoryIT {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryFactory categoryFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.categoryRepository).isNotNull();
	}

	@Test
	void deleteOne() {
		// create test environment
		Category category = this.categoryFactory.category("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.categoryRepository.delete(category);

		// verify the results
		assertThat(this.categoryRepository.findById(category.getId()).isPresent()).isFalse();
	}

	@Test
	void saveOne() {
		// execute the operation to be tested
		Category category = this.categoryFactory.transport();

		// verify the results
		assertThat(category.getId()).isNotNull();
	}

}
