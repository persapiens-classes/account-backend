package org.persapiens.account.controller;

import jakarta.validation.Valid;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CategoryController<C extends Category>
		extends CrudController<CategoryDTO, CategoryDTO, CategoryDTO, String, C, Long> {

	private CategoryService<C> categoryService;

	public CategoryController(CategoryService<C> categoryService) {
		super(categoryService);

		this.categoryService = categoryService;
	}

	@GetMapping("/{description}")
	public CategoryDTO findByDescription(@PathVariable(required = true) String description) {
		return this.categoryService.findByDescription(description);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable(required = true) String description) {
		this.categoryService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public CategoryDTO update(@PathVariable(required = true) String description,
			@Valid @RequestBody(required = true) CategoryDTO categoryDTO) {
		return this.categoryService.update(description, categoryDTO);
	}

}
