package org.persapiens.account.service;

import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.EquityCategoryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOFactory {

	@Autowired
	private EquityCategoryFactory categoryFactory;

	public CategoryDTO categoryDTO(Category category) {
		return new CategoryDTO(category.getDescription());
	}

	public CategoryDTO categoryDTO(String description) {
		return categoryDTO(this.categoryFactory.category(description));
	}

	public CategoryDTO transport() {
		return categoryDTO(EquityCategoryConstants.TRANSPORT);
	}

	public CategoryDTO tax() {
		return categoryDTO(EquityCategoryConstants.TAX);
	}

	public CategoryDTO salary() {
		return categoryDTO(EquityCategoryConstants.SALARY);
	}

	public CategoryDTO cash() {
		return categoryDTO(EquityCategoryConstants.CASH);
	}

	public CategoryDTO bank() {
		return categoryDTO(EquityCategoryConstants.BANK);
	}

}
