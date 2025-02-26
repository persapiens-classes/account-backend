package org.persapiens.account.controller;

import jakarta.validation.Valid;
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
public class CategoryController extends CrudController<CategoryDTO, CategoryDTO, CategoryDTO, String, Category, Long> {

	private CategoryService categoryService;

	@GetMapping("/{description}")
	public CategoryDTO findByDescription(@PathVariable(required = true) String description) {
		return this.categoryService.findByDescription(description);
	}

	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable(required = true) String description) {
		this.categoryService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public CategoryDTO update(@PathVariable(required = true) String description,
			@Valid @RequestBody(required = true) CategoryDTO dto) {
		return this.categoryService.update(description, dto);
	}

}
