package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
		return CategoryDTO.builder().description(entity.getDescription()).build();
	}

	private Category toEntity(CategoryDTO dto) {
		return Category.builder().description(dto.getDescription()).build();
	}

	@Override
	protected Category insertDtoToEntity(CategoryDTO dto) {
		return toEntity(dto);
	}

	@Override
	protected Category updateDtoToEntity(CategoryDTO dto) {
		return toEntity(dto);
	}

	@Override
	protected Optional<Category> findByUpdateKey(String updateKey) {
		return this.categoryRepository.findByDescription(updateKey);
	}

	@Override
	protected Category setIdToUpdate(Category t, Category updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	public CategoryDTO findByDescription(String description) {
		Optional<Category> categoryOptional = this.categoryRepository.findByDescription(description);
		if (categoryOptional.isPresent()) {
			return toDTO(categoryOptional.get());
		}
		else {
			throw new BeanNotFoundException("Bean not found by: " + description);
		}
	}

	@Transactional
	public void deleteByDescription(String description) {
		if (this.categoryRepository.deleteByDescription(description) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + description);
		}
	}

	private void validate(CategoryDTO categoryDto) {
		if (StringUtils.isBlank(categoryDto.getDescription())) {
			throw new IllegalArgumentException("Description empty!");
		}
		if (this.categoryRepository.findByDescription(categoryDto.getDescription()).isPresent()) {
			throw new BeanExistsException("Description exists: " + categoryDto.getDescription());
		}
	}

	@Override
	public CategoryDTO insert(CategoryDTO insertDto) {
		validate(insertDto);

		return super.insert(insertDto);
	}

	@Override
	public CategoryDTO update(String updateKey, CategoryDTO updateDto) {
		validate(updateDto);

		return super.update(updateKey, updateDto);
	}

	private CategoryDTO categoryDTO(String description) {
		Optional<Category> findByDescricao = this.categoryRepository.findByDescription(description);
		if (findByDescricao.isEmpty()) {
			CategoryDTO result = CategoryDTO.builder().description(description).build();
			return insert(result);
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
