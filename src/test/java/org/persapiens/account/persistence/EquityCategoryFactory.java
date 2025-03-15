package org.persapiens.account.persistence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.domain.EquityCategory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EquityCategoryFactory {

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
