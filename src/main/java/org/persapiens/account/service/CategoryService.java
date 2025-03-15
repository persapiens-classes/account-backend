package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.transaction.annotation.Transactional;

public abstract class CategoryService<C extends Category>
		extends CrudService<CategoryDTO, CategoryDTO, CategoryDTO, String, C, Long> {

	private CategoryRepository<C> categoryRepository;

	protected CategoryService(CategoryRepository<C> categoryRepository) {
		super(categoryRepository);

		this.categoryRepository = categoryRepository;
	}

	@Override
	protected CategoryDTO toDTO(Category entity) {
		return new CategoryDTO(entity.getDescription());
	}

	private void validate(CategoryDTO categoryDto) {
		if (this.categoryRepository.findByDescription(categoryDto.description()).isPresent()) {
			throw new BeanExistsException("Description exists: " + categoryDto.description());
		}
	}

	protected abstract C createCategory();

	private C toEntity(CategoryDTO categoryDTO) {
		validate(categoryDTO);
		C result = createCategory();
		result.setDescription(categoryDTO.description());
		return result;
	}

	@Override
	protected C insertDtoToEntity(CategoryDTO categoryDTO) {
		return toEntity(categoryDTO);
	}

	@Override
	protected C updateDtoToEntity(CategoryDTO categoryDTO) {
		return toEntity(categoryDTO);
	}

	C findEntityByDescription(String description) {
		Optional<C> categoryOptional = this.categoryRepository.findByDescription(description);
		if (categoryOptional.isPresent()) {
			return categoryOptional.get();
		}
		else {
			throw new BeanNotFoundException("Category not found by: " + description);
		}
	}

	@Override
	protected C findByUpdateKey(String updateKey) {
		return findEntityByDescription(updateKey);
	}

	@Override
	protected C setIdToUpdate(C t, C updateEntity) {
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
