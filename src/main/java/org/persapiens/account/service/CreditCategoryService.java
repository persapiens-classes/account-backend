package org.persapiens.account.service;

import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.persistence.CreditCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditCategoryService extends CategoryService<CreditCategory> {

	public CreditCategoryService(CreditCategoryRepository creditCategoryRepository) {
		super(creditCategoryRepository);
	}

	@Override
	protected CreditCategory createCategory() {
		return new CreditCategory();
	}
}
