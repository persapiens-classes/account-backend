package org.persapiens.account.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTests {

	private static final String TRANSPORT = "transport";
	private static final String GASOLINE = "gasoline";
	private static final String TAX = "tax";
	private static final String HOUSING = "housing";

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
