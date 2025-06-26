package org.persapiens.account.controller;

import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.service.CategoryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equityCategories")
public class EquityCategoryController extends CategoryController<EquityCategory> {

	public EquityCategoryController(CategoryService<EquityCategory> equityCategoryService) {
		super(equityCategoryService);
	}

}
