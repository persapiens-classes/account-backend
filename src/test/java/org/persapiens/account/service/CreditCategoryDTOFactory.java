package org.persapiens.account.service;

import org.persapiens.account.common.CreditCategoryConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CreditCategoryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCategoryDTOFactory {

	@Autowired
	private CreditCategoryFactory categoryFactory;

	public CategoryDTO categoryDTO(Category category) {
		return new CategoryDTO(category.getDescription());
	}

	public CategoryDTO categoryDTO(String description) {
		return categoryDTO(this.categoryFactory.category(description));
	}

	public CategoryDTO salary() {
		return categoryDTO(CreditCategoryConstants.SALARY);
	}

}
