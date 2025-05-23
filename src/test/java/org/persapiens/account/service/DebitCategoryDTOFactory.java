package org.persapiens.account.service;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.DebitCategoryConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.DebitCategoryFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DebitCategoryDTOFactory {

	private DebitCategoryFactory categoryFactory;

	public CategoryDTO categoryDTO(Category category) {
		return new CategoryDTO(category.getDescription());
	}

	public CategoryDTO categoryDTO(String description) {
		return categoryDTO(this.categoryFactory.category(description));
	}

	public CategoryDTO transport() {
		return categoryDTO(DebitCategoryConstants.TRANSPORT);
	}

	public CategoryDTO tax() {
		return categoryDTO(DebitCategoryConstants.TAX);
	}

}
