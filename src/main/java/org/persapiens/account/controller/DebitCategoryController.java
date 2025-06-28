package org.persapiens.account.controller;

import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.service.CategoryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debitCategories")
public class DebitCategoryController extends CategoryController<DebitCategory> {

	public DebitCategoryController(CategoryService<DebitCategory> debitCategoryService) {
		super(debitCategoryService);
	}

}
