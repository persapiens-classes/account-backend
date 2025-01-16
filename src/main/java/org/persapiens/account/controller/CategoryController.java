package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.service.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController extends CrudController<CategoryDTO, CategoryDTO, Category, Long> {

	private CategoryService categoryService;

	@Override
	protected Category toEntity(CategoryDTO dto) {
		return Category.builder().description(dto.getDescription()).build();
	}

	@Override
	protected CategoryDTO toDTO(Category entity) {
		return CategoryDTO.builder().description(entity.getDescription()).build();
	}

	@Override
	protected Category insertDtoToEntity(CategoryDTO dto) {
		return Category.builder().description(dto.getDescription()).build();
	}

	@GetMapping("/{description}")
	public Optional<CategoryDTO> findByDescription(@PathVariable String description) {
		return toDTOOptional(this.categoryService.findByDescription(description));
	}

	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable String description) {
		this.categoryService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public CategoryDTO update(@PathVariable String description, @RequestBody CategoryDTO dto) {
		CategoryDTO result = null;
		Optional<Category> categoryOptional = this.categoryService.findByDescription(description);
		if (categoryOptional.isPresent()) {
			Category currentCategory = categoryOptional.get();
			Category entityToSave = toEntity(dto);
			entityToSave.setId(currentCategory.getId());
			Category saved = this.categoryService.save(entityToSave);
			result = toDTO(saved);
		}
		return result;
	}

}
