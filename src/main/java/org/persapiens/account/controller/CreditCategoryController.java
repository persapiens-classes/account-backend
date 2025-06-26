package org.persapiens.account.controller;

import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.service.CategoryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditCategories")
public class CreditCategoryController extends CategoryController<CreditCategory> {

	public CreditCategoryController(CategoryService<CreditCategory> creditCategoryService) {
		super(creditCategoryService);
	}

}
