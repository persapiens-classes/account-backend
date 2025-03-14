package org.persapiens.account.controller;

import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.service.EquityCategoryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equityCategories")
public class EquityCategoryController extends CategoryController<EquityCategory> {

	public EquityCategoryController(EquityCategoryService equityCategoryService) {
		super(equityCategoryService);
	}

}
