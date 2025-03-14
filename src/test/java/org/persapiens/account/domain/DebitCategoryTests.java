package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityCategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class EquityCategoryTests {

	@Test
	void equalDescriptions() {
		assertThat(Category.builder().description(EquityCategoryConstants.TRANSPORT).build())
			.isEqualTo(Category.builder().description(EquityCategoryConstants.TRANSPORT).build());
	}

	@Test
	void differentDescriptions() {
		assertThat(Category.builder().description(EquityCategoryConstants.TRANSPORT).build())
			.isNotEqualTo(Category.builder().description(EquityCategoryConstants.HOUSING).build());
	}

	@Test
	void compareTo() {
		Set<Category> categories = new TreeSet<>();

		Category transport = Category.builder().description(EquityCategoryConstants.TRANSPORT).build();
		categories.add(transport);
		Category housing = Category.builder().description(EquityCategoryConstants.HOUSING).build();
		categories.add(housing);

		assertThat(categories.iterator().next()).isEqualTo(housing);
	}

}
