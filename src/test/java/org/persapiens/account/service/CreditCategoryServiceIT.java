package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CreditCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreditCategoryServiceIT {

	@Autowired
	private CreditCategoryRepository creditCategoryRepository;

	@Autowired
	private CreditCategoryService creditCategoryService;

	@Autowired
	private CreditCategoryDTOFactory creditCategoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.creditCategoryService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		CategoryDTO categoryDTO = this.creditCategoryDTOFactory.salary();

		// verify the results
		assertThat(this.creditCategoryService.findByDescription(categoryDTO.description())).isEqualTo(categoryDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		CategoryDTO categoryDTO = this.creditCategoryDTOFactory.categoryDTO("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.creditCategoryService.deleteByDescription(categoryDTO.description());

		// verify the results
		assertThat(this.creditCategoryRepository.findByDescription(categoryDTO.description())).isNotPresent();
	}

}
