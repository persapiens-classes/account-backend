package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.domain.EquityCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquityCategoryFactory {

	@Autowired
	private EquityCategoryRepository categoryRepository;

	public EquityCategory category(String description) {
		Optional<EquityCategory> findByDescription = this.categoryRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			EquityCategory category = EquityCategory.builder().description(description).build();
			return this.categoryRepository.save(category);
		}
		else {
			return findByDescription.get();
		}
	}

	public EquityCategory cash() {
		return category(EquityCategoryConstants.CASH);
	}

	public EquityCategory bank() {
		return category(EquityCategoryConstants.BANK);
	}

}
