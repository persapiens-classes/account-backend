package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityCategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class EquityCategoryTests {

	@Test
	void equalDescriptions() {
		assertThat(Category.builder().description(EquityCategoryConstants.BANK).build())
			.isEqualTo(Category.builder().description(EquityCategoryConstants.BANK).build());
	}

	@Test
	void differentDescriptions() {
		assertThat(Category.builder().description(EquityCategoryConstants.BANK).build())
			.isNotEqualTo(Category.builder().description(EquityCategoryConstants.CASH).build());
	}

	@Test
	void compareTo() {
		Set<Category> categories = new TreeSet<>();

		Category cash = Category.builder().description(EquityCategoryConstants.CASH).build();
		categories.add(cash);
		Category bank = Category.builder().description(EquityCategoryConstants.BANK).build();
		categories.add(bank);

		assertThat(categories.iterator().next()).isEqualTo(bank);
	}

}
