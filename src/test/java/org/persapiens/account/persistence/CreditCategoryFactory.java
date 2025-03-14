package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.common.CreditCategoryConstants;
import org.persapiens.account.domain.CreditCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCategoryFactory {

	@Autowired
	private CreditCategoryRepository categoryRepository;

	public CreditCategory category(String description) {
		Optional<CreditCategory> findByDescription = this.categoryRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			CreditCategory category = CreditCategory.builder().description(description).build();
			return this.categoryRepository.save(category);
		}
		else {
			return findByDescription.get();
		}
	}

	public CreditCategory salary() {
		return category(CreditCategoryConstants.SALARY);
	}

}
