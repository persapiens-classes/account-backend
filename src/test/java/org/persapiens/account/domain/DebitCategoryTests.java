package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.DebitCategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCategoryTests {

	@Test
	void equalDescriptions() {
		assertThat(Category.builder().description(DebitCategoryConstants.TRANSPORT).build())
			.isEqualTo(Category.builder().description(DebitCategoryConstants.TRANSPORT).build());
	}

	@Test
	void differentDescriptions() {
		assertThat(Category.builder().description(DebitCategoryConstants.TRANSPORT).build())
			.isNotEqualTo(Category.builder().description(DebitCategoryConstants.HOUSING).build());
	}

	@Test
	void compareTo() {
		Set<Category> categories = new TreeSet<>();

		Category transport = Category.builder().description(DebitCategoryConstants.TRANSPORT).build();
		categories.add(transport);
		Category housing = Category.builder().description(DebitCategoryConstants.HOUSING).build();
		categories.add(housing);

		assertThat(categories.iterator().next()).isEqualTo(housing);
	}

}
