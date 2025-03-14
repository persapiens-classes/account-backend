package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityCategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class EquityCategoryTests {

	@Test
	void equalDescriptions() {
		assertThat(EquityCategory.builder().description(EquityCategoryConstants.BANK).build())
			.isEqualTo(EquityCategory.builder().description(EquityCategoryConstants.BANK).build());
	}

	@Test
	void differentDescriptions() {
		assertThat(EquityCategory.builder().description(EquityCategoryConstants.BANK).build())
			.isNotEqualTo(EquityCategory.builder().description(EquityCategoryConstants.CASH).build());
	}

	@Test
	void compareTo() {
		Set<EquityCategory> categories = new TreeSet<>();

		EquityCategory bank = EquityCategory.builder().description(EquityCategoryConstants.BANK).build();
		categories.add(bank);
		EquityCategory cash = EquityCategory.builder().description(EquityCategoryConstants.CASH).build();
		categories.add(cash);

		assertThat(categories.iterator().next()).isEqualTo(cash);
	}

}
