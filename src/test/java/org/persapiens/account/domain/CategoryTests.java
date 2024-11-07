package org.persapiens.account.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.DebitAccountConstants;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTests {

	@Test
	public void equalDescriptions() {
		assertThat(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.isEqualTo(Category.builder().description(CategoryConstants.TRANSPORT).build());
	}

	@Test
	public void equalDescriptionsWithDifferentAccounts() {
		Category categoryTransporte1 = Category.builder().description(CategoryConstants.TRANSPORT).build();

		Set<Account> accounts = new HashSet<>();
		accounts.add(DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(categoryTransporte1)
			.build());
		accounts
			.add(DebitAccount.builder().description(DebitAccountConstants.BUS).category(categoryTransporte1).build());
		categoryTransporte1.setAccounts(accounts);

		Category categoryTransporte2 = Category.builder().description(CategoryConstants.TRANSPORT).build();

		assertThat(categoryTransporte1).isEqualTo(categoryTransporte2);
	}

	@Test
	public void differentDescriptions() {
		assertThat(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.isNotEqualTo(Category.builder().description(CategoryConstants.HOUSING).build());
	}

	@Test
	public void compareTo() {
		Set<Category> categories = new TreeSet<>();

		Category transport = Category.builder().description(CategoryConstants.TRANSPORT).build();
		categories.add(transport);
		Category housing = Category.builder().description(CategoryConstants.HOUSING).build();
		categories.add(housing);

		assertThat(categories.iterator().next()).isEqualTo(housing);
	}

}
