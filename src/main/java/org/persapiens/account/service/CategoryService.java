package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public abstract class CategoryService <T extends Category> extends CrudService<CategoryDTO, CategoryDTO, CategoryDTO, String, T, Long> {

	private CategoryRepository<T> categoryRepository;

	protected abstract T createCategory();

	@Override
	protected CategoryDTO toDTO(T entity) {
		return new CategoryDTO(entity.getDescription());
	}

	private void validate(CategoryDTO categoryDto) {
		if (this.categoryRepository.findByDescription(categoryDto.description()).isPresent()) {
			throw new BeanExistsException("Description exists: " + categoryDto.description());
		}
	}

	private T toEntity(CategoryDTO categoryDTO) {
		validate(categoryDTO);

		T result = createCategory();
		result.setDescription(categoryDTO.description());

		return result;
	}

	@Override
	protected T insertDtoToEntity(CategoryDTO categoryDTO) {
		return toEntity(categoryDTO);
	}

	@Override
	protected T updateDtoToEntity(CategoryDTO categoryDTO) {
		return toEntity(categoryDTO);
	}

	T findEntityByDescription(String description) {
		Optional<T> categoryOptional = this.categoryRepository.findByDescription(description);
		if (categoryOptional.isPresent()) {
			return categoryOptional.get();
		}
		else {
			throw new BeanNotFoundException("Category not found by: " + description);
		}
	}

	@Override
	protected T findByUpdateKey(String updateKey) {
		return findEntityByDescription(updateKey);
	}

	@Override
	protected T setIdToUpdate(T t, T updateEntity) {
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

}
