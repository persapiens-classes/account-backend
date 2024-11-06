package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.Category;
import org.persapiens.account.persistence.CategoryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CategoryServiceIT {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryFactory categoryFactory;

	@Test
	public void repositoryNotNull() {
		assertThat(this.categoryService).isNotNull();
	}

	@Test
	public void saveOne() {
		// create test environment
		Category category = this.categoryFactory.bank();

		// verify the results
		assertThat(this.categoryService.findById(category.getId()).get()).isEqualTo(category);
	}

	@Test
	public void deleteOne() {
		// create test environment
		Category category = this.categoryFactory.category("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.categoryService.delete(category);

		// verify the results
		assertThat(this.categoryService.findById(category.getId()).isPresent()).isFalse();
	}

}
