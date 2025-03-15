package org.persapiens.account.service;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.EquityCategoryFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EquityCategoryDTOFactory {

	private EquityCategoryFactory categoryFactory;

	public CategoryDTO categoryDTO(Category category) {
		return new CategoryDTO(category.getDescription());
	}

	public CategoryDTO categoryDTO(String description) {
		return categoryDTO(this.categoryFactory.category(description));
	}

	public CategoryDTO cash() {
		return categoryDTO(EquityCategoryConstants.CASH);
	}

	public CategoryDTO bank() {
		return categoryDTO(EquityCategoryConstants.BANK);
	}

}
