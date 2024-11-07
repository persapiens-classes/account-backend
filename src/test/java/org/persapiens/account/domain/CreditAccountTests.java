package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditAccountTests {

	public void equalDescriptionAndCategory() {
		assertThat(CreditAccount.builder()
			.description(CreditAccountConstants.STOCK)
			.category(Category.builder().description(CreditAccountConstants.DONATION).build())
			.build())
			.isEqualTo(CreditAccount.builder()
				.description(CreditAccountConstants.STOCK)
				.category(Category.builder().description(CreditAccountConstants.DONATION).build())
				.build());
	}

	@Test
	public void equalDescriptionAndDifferentCategory() {
		assertThat(CreditAccount.builder()
			.description(CreditAccountConstants.INTERNSHIP)
			.category(Category.builder().description(CategoryConstants.SALARY).build())
			.build())
			.isNotEqualTo(CreditAccount.builder()
				.description(CreditAccountConstants.INTERNSHIP)
				.category(Category.builder().description(CategoryConstants.CASH).build())
				.build());
	}

	@Test
	public void differentDescriptionAndEqualCategory() {
		assertThat(CreditAccount.builder()
			.description(CreditAccountConstants.STOCK)
			.category(Category.builder().description(CategoryConstants.SALARY).build())
			.build())
			.isNotEqualTo(CreditAccount.builder()
				.description(CreditAccountConstants.WORK)
				.category(Category.builder().description(CategoryConstants.SALARY).build())
				.build());
	}

	@Test
	public void equalDescriptionWithoutCategory() {
		Assertions
			.assertThatThrownBy(() -> CreditAccount.builder()
				.description(CreditAccountConstants.STOCK)
				.build()
				.equals(CreditAccount.builder().description(CreditAccountConstants.STOCK).build()))
			.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void compareTo() {
		Set<CreditAccount> creditAccounts = new TreeSet<>();

		CreditAccount work = CreditAccount.builder()
			.description(CreditAccountConstants.WORK)
			.category(Category.builder().description(CreditAccountConstants.SALARY).build())
			.build();
		creditAccounts.add(work);
		CreditAccount donation = CreditAccount.builder()
			.description(CreditAccountConstants.DONATION)
			.category(Category.builder().description(CreditAccountConstants.SALARY).build())
			.build();
		creditAccounts.add(donation);

		assertThat(creditAccounts.iterator().next()).isEqualTo(donation);
	}

}
