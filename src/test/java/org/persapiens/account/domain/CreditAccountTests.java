package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.persapiens.account.common.CreditAccountConstants.STOCK;
import static org.persapiens.account.common.CreditAccountConstants.DONATION;
import static org.persapiens.account.common.CreditAccountConstants.SALARY;
import static org.persapiens.account.common.CreditAccountConstants.WORK;

public class CreditAccountTests {

	public void equalDescriptionAndCategory() {
		assertThat(CreditAccount.builder().description(STOCK)
				.category(Category.builder().description(DONATION).build()).build())
			.isEqualTo(CreditAccount.builder().description(STOCK)
				.category(Category.builder().description(DONATION).build()).build());
	}

	@Test
	public void equalDescriptionAndDifferentCategory() {
		assertThat(CreditAccount.builder().description(STOCK)
				.category(Category.builder().description(DONATION).build()).build())
			.isNotEqualTo(CreditAccount.builder().description(STOCK)
				.category(Category.builder().description(SALARY).build()).build());
	}

	@Test
	public void differentDescriptionAndEqualCategory() {
		assertThat(CreditAccount.builder().description(STOCK)
				.category(Category.builder().description(DONATION).build()).build())
			.isNotEqualTo(CreditAccount.builder().description(WORK)
				.category(Category.builder().description(DONATION).build()).build());
	}

	@Test
	public void equalDescriptionWithoutCategory() {
		NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
			CreditAccount.builder().description(STOCK).build()
				.equals(CreditAccount.builder().description(STOCK).build());
		});
		assertThat(thrown)
			.isNotNull();
	}

	@Test
	public void compareTo() {
		Set<CreditAccount> creditAccounts = new TreeSet<>();

		CreditAccount work = CreditAccount.builder().description(WORK)
			.category(Category.builder().description(SALARY).build())
			.build();
		creditAccounts.add(work);
		CreditAccount donation = CreditAccount.builder().description(DONATION)
			.category(Category.builder().description(SALARY).build())
			.build();
		creditAccounts.add(donation);

		assertThat(creditAccounts.iterator().next()).isEqualTo(donation);
	}
}
