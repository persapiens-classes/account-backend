package org.persapiens.account.persistence;

import java.util.Optional;

import static org.persapiens.account.common.CategoryConstants.CASH;
import static org.persapiens.account.common.CategoryConstants.SALARY;
import static org.persapiens.account.common.CategoryConstants.TAX;
import static org.persapiens.account.common.CategoryConstants.TRANSPORT;
import static org.persapiens.account.common.CategoryConstants.BANK;
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
		return category(TRANSPORT);
	}

	public Category tax() {
		return category(TAX);
	}

	public Category salary() {
		return category(SALARY);
	}

	public Category cash() {
		return category(CASH);
	}

	public Category bank() {
		return category(BANK);
	}

}
