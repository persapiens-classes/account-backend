package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.common.DebitCategoryConstants;
import org.persapiens.account.domain.DebitCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitCategoryFactory {

	@Autowired
	private DebitCategoryRepository categoryRepository;

	public DebitCategory category(String description) {
		Optional<DebitCategory> findByDescription = this.categoryRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			DebitCategory category = DebitCategory.builder().description(description).build();
			return this.categoryRepository.save(category);
		}
		else {
			return findByDescription.get();
		}
	}

	public DebitCategory transport() {
		return category(DebitCategoryConstants.TRANSPORT);
	}

	public DebitCategory tax() {
		return category(DebitCategoryConstants.TAX);
	}


}
