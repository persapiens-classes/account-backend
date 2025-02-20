package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryServiceIT {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryDTOFactory categoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.categoryService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		CategoryDTO categoryDTO = this.categoryDTOFactory.bank();

		// verify the results
		assertThat(this.categoryService.findByDescription(categoryDTO.description())).isEqualTo(categoryDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		CategoryDTO categoryDTO = this.categoryDTOFactory.categoryDTO("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.categoryService.deleteByDescription(categoryDTO.description());

		// verify the results
		assertThat(this.categoryRepository.findByDescription(categoryDTO.description())).isNotPresent();
	}

	@Test
	void expenseTransfer() {
		assertThat(this.categoryService.expenseTransfer()).isEqualTo(this.categoryService.expenseTransfer());
	}

	@Test
	void incomeTransfer() {
		assertThat(this.categoryService.incomeTransfer()).isEqualTo(this.categoryService.incomeTransfer());
	}

}
