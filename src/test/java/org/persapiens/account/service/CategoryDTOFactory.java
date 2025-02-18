package org.persapiens.account.service;

import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CategoryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOFactory {

	@Autowired
	private CategoryFactory categoryFactory;

	public CategoryDTO categoryDTO(Category category) {
		return new CategoryDTO(category.getDescription());
	}

	public CategoryDTO categoryDTO(String description) {
		return categoryDTO(this.categoryFactory.category(description));
	}

	public CategoryDTO transport() {
		return categoryDTO(CategoryConstants.TRANSPORT);
	}

	public CategoryDTO tax() {
		return categoryDTO(CategoryConstants.TAX);
	}

	public CategoryDTO salary() {
		return categoryDTO(CategoryConstants.SALARY);
	}

	public CategoryDTO cash() {
		return categoryDTO(CategoryConstants.CASH);
	}

	public CategoryDTO bank() {
		return categoryDTO(CategoryConstants.BANK);
	}

}
