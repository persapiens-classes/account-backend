package org.persapiens.account.service;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.CreditCategoryConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CreditCategoryFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreditCategoryDTOFactory {

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
