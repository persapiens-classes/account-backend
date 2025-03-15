package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.CreditCategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCategoryTests {

	@Test
	void equalDescriptions() {
		assertThat(CreditCategory.builder().description(CreditCategoryConstants.SALARY).build())
			.isEqualTo(CreditCategory.builder().description(CreditCategoryConstants.SALARY).build());
	}

	@Test
	void differentDescriptions() {
		assertThat(CreditCategory.builder().description(CreditCategoryConstants.SALARY).build())
			.isNotEqualTo(CreditCategory.builder().description(CreditCategoryConstants.INTERIM).build());
	}

	@Test
	void compareTo() {
		Set<CreditCategory> categories = new TreeSet<>();

		CreditCategory salary = CreditCategory.builder().description(CreditCategoryConstants.SALARY).build();
		categories.add(salary);
		CreditCategory interim = CreditCategory.builder().description(CreditCategoryConstants.INTERIM).build();
		categories.add(interim);

		assertThat(categories.iterator().next()).isEqualTo(interim);
	}

}
