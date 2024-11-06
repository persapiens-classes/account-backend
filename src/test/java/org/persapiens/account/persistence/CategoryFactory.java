package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.domain.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

	@Autowired
	private CategoryRepository categoryRepository;

	public Category category(String description) {
		Optional<Category> findByDescription = this.categoryRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			Category category = Category.builder().description(description).build();
			return this.categoryRepository.save(category);
		}
		else {
			return findByDescription.get();
		}
	}

	public Category transport() {
		return category(CategoryConstants.TRANSPORT);
	}

	public Category tax() {
		return category(CategoryConstants.TAX);
	}

	public Category salary() {
		return category(CategoryConstants.SALARY);
	}

	public Category cash() {
		return category(CategoryConstants.CASH);
	}

	public Category bank() {
		return category(CategoryConstants.BANK);
	}

}
