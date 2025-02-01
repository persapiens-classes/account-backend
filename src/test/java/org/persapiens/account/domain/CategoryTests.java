package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.CategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTests {

	@Test
	void equalDescriptions() {
		assertThat(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.isEqualTo(Category.builder().description(CategoryConstants.TRANSPORT).build());
	}

	@Test
	void differentDescriptions() {
		assertThat(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.isNotEqualTo(Category.builder().description(CategoryConstants.HOUSING).build());
	}

	@Test
	void compareTo() {
		Set<Category> categories = new TreeSet<>();

		Category transport = Category.builder().description(CategoryConstants.TRANSPORT).build();
		categories.add(transport);
		Category housing = Category.builder().description(CategoryConstants.HOUSING).build();
		categories.add(housing);

		assertThat(categories.iterator().next()).isEqualTo(housing);
	}

}
