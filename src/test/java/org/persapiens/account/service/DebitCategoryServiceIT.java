package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.DebitCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DebitCategoryServiceIT {

	@Autowired
	private DebitCategoryRepository debitCategoryRepository;

	@Autowired
	private CategoryService<DebitCategory> debitCategoryService;

	@Autowired
	private DebitCategoryDTOFactory debitCategoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.debitCategoryService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		CategoryDTO categoryDTO = this.debitCategoryDTOFactory.transport();

		// verify the results
		assertThat(this.debitCategoryService.findByDescription(categoryDTO.description())).isEqualTo(categoryDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		CategoryDTO categoryDTO = this.debitCategoryDTOFactory.categoryDTO("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.debitCategoryService.deleteByDescription(categoryDTO.description());

		// verify the results
		assertThat(this.debitCategoryRepository.findByDescription(categoryDTO.description())).isNotPresent();
	}

}
