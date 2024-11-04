package org.persapiens.account.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.persapiens.account.common.CategoryConstants.GASOLINE;
import static org.persapiens.account.common.CategoryConstants.HOUSING;
import static org.persapiens.account.common.CategoryConstants.TAX;
import static org.persapiens.account.common.CategoryConstants.TRANSPORT;

public class CategoryTests {


	@Test
	public void equalDescriptions() {
		assertThat(Category.builder().description(TRANSPORT).build())
			.isEqualTo(Category.builder().description(TRANSPORT).build());
	}

	@Test
	public void equalDescriptionsWithDifferentAccounts() {
		Category CategoryTransporte1 = Category.builder().description(TRANSPORT)
			.build();

		Set<Account> Accounts = new HashSet<>();
		Accounts.add(DebitAccount.builder().description(GASOLINE).category(CategoryTransporte1).build());
		Accounts.add(DebitAccount.builder().description(TAX).category(CategoryTransporte1).build());
		CategoryTransporte1.setAccounts(Accounts);

		Category CategoryTransporte2 = Category.builder().description(TRANSPORT).build();

		assertThat(CategoryTransporte1).isEqualTo(CategoryTransporte2);
	}

	@Test
	public void differentDescriptions() {
		assertThat(Category.builder().description(TRANSPORT).build())
			.isNotEqualTo(Category.builder().description(HOUSING).build());
	}

	@Test
	public void compareTo() {
		Set<Category> categories = new TreeSet<>();

		Category transport = Category.builder().description(TRANSPORT).build();
		categories.add(transport);
		Category housing = Category.builder().description(HOUSING).build();
		categories.add(housing);

		assertThat(categories.iterator().next()).isEqualTo(housing);
	}}
