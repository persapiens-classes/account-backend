package org.persapiens.account.service;

import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.persistence.EquityCategoryRepository;

import org.springframework.stereotype.Service;

@Service
public class EquityCategoryService extends CategoryService<EquityCategory> {

	public EquityCategoryService(EquityCategoryRepository equityCategoryRepository) {
		super(equityCategoryRepository);
	}

	@Override
	protected EquityCategory createCategory() {
		return new EquityCategory();
	}

}
