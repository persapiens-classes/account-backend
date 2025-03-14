package org.persapiens.account.service;

import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.persistence.DebitCategoryRepository;

import org.springframework.stereotype.Service;

@Service
public class DebitCategoryService extends CategoryService<DebitCategory> {

	public DebitCategoryService(DebitCategoryRepository debitCategoryRepository) {
		super(debitCategoryRepository);
	}

	@Override
	protected DebitCategory createCategory() {
		return new DebitCategory();
	}

}
