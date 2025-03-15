package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.CreditCategoryConstants;

import static org.assertj.core.api.Assertions.assertThat;

class CreditAccountTests {

	void equalDescriptionAndCategory() {
		assertThat(CreditAccount.builder()
			.description(CreditAccountConstants.STOCK)
			.category(CreditCategory.builder().description(CreditAccountConstants.DONATION).build())
			.build())
			.isEqualTo(CreditAccount.builder()
				.description(CreditAccountConstants.STOCK)
				.category(CreditCategory.builder().description(CreditAccountConstants.DONATION).build())
				.build());
	}

	@Test
	void equalDescriptionAndDifferentCategory() {
		assertThat(CreditAccount.builder()
			.description(CreditAccountConstants.INTERNSHIP)
			.category(CreditCategory.builder().description(CreditCategoryConstants.SALARY).build())
			.build())
			.isNotEqualTo(CreditAccount.builder()
				.description(CreditAccountConstants.INTERNSHIP)
				.category(CreditCategory.builder().description(CreditCategoryConstants.INTERIM).build())
				.build());
	}

	@Test
	void differentDescriptionAndEqualCategory() {
		assertThat(CreditAccount.builder()
			.description(CreditAccountConstants.STOCK)
			.category(CreditCategory.builder().description(CreditCategoryConstants.SALARY).build())
			.build())
			.isNotEqualTo(CreditAccount.builder()
				.description(CreditAccountConstants.WORK)
				.category(CreditCategory.builder().description(CreditCategoryConstants.SALARY).build())
				.build());
	}

	@Test
	void equalDescriptionWithoutCategory() {
		var builder = CreditAccount.builder().description(CreditAccountConstants.STOCK);
		Assertions.assertThatThrownBy(builder::build).isInstanceOf(NullPointerException.class);
	}

	@Test
	void compareTo() {
		Set<CreditAccount> creditAccounts = new TreeSet<>();

		CreditAccount work = CreditAccount.builder()
			.description(CreditAccountConstants.WORK)
			.category(CreditCategory.builder().description(CreditAccountConstants.SALARY).build())
			.build();
		creditAccounts.add(work);
		CreditAccount donation = CreditAccount.builder()
			.description(CreditAccountConstants.DONATION)
			.category(CreditCategory.builder().description(CreditAccountConstants.SALARY).build())
			.build();
		creditAccounts.add(donation);

		assertThat(creditAccounts.iterator().next()).isEqualTo(donation);
	}

}
