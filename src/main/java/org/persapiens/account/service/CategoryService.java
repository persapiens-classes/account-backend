package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Category;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CategoryService extends CrudService<Category, Long> {

	private CategoryRepository categoryRepository;

	public Optional<Category> findByDescription(String description) {
		return this.categoryRepository.findByDescription(description);
	}

	@Transactional
	public void deleteByDescription(String description) {
		this.categoryRepository.deleteByDescription(description);
	}

	private Category category(String description) {
		Optional<Category> findByDescricao = findByDescription(description);
		if (findByDescricao.isEmpty()) {
			Category result = Category.builder().description(description).build();
			return save(result);
		}
		else {
			return findByDescricao.get();
		}
	}

	public Category expenseTransfer() {
		return category(Category.EXPENSE_TRANSFER_CATEGORY);
	}

	public Category incomeTransfer() {
		return category(Category.INCOME_TRANSFER_CATEGORY);
	}

}
