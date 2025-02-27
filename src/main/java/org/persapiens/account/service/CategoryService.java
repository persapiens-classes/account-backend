package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CategoryService extends CrudService<CategoryDTO, CategoryDTO, CategoryDTO, String, Category, Long> {

	private CategoryRepository categoryRepository;

	@Override
	protected CategoryDTO toDTO(Category entity) {
		return new CategoryDTO(entity.getDescription());
	}

	private void validate(CategoryDTO categoryDto) {
		if (this.categoryRepository.findByDescription(categoryDto.description()).isPresent()) {
			throw new BeanExistsException("Description exists: " + categoryDto.description());
		}
	}

	private Category toEntity(CategoryDTO categoryDTO) {
		validate(categoryDTO);
		return Category.builder().description(categoryDTO.description()).build();
	}

	@Override
	protected Category insertDtoToEntity(CategoryDTO categoryDTO) {
		return toEntity(categoryDTO);
	}

	@Override
	protected Category updateDtoToEntity(CategoryDTO categoryDTO) {
		return toEntity(categoryDTO);
	}

	Category findEntityByDescription(String description) {
		Optional<Category> categoryOptional = this.categoryRepository.findByDescription(description);
		if (categoryOptional.isPresent()) {
			return categoryOptional.get();
		}
		else {
			throw new BeanNotFoundException("Category not found by: " + description);
		}
	}

	@Override
	protected Category findByUpdateKey(String updateKey) {
		return findEntityByDescription(updateKey);
	}

	@Override
	protected Category setIdToUpdate(Category t, Category updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	public CategoryDTO findByDescription(String description) {
		return toDTO(findEntityByDescription(description));
	}

	@Transactional
	public void deleteByDescription(String description) {
		if (this.categoryRepository.deleteByDescription(description) == 0) {
			throw new BeanNotFoundException("Category not found by: " + description);
		}
	}

	private CategoryDTO categoryDTO(String description) {
		Optional<Category> findByDescricao = this.categoryRepository.findByDescription(description);
		if (findByDescricao.isEmpty()) {
			Category result = Category.builder().description(description).build();
			return toDTO(this.categoryRepository.save(result));
		}
		else {
			return toDTO(findByDescricao.get());
		}
	}

	@Transactional
	public CategoryDTO expenseTransfer() {
		return categoryDTO(Category.EXPENSE_TRANSFER_CATEGORY);
	}

	@Transactional
	public CategoryDTO incomeTransfer() {
		return categoryDTO(Category.INCOME_TRANSFER_CATEGORY);
	}

}
