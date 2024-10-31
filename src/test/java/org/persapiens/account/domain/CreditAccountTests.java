package org.persapiens.account.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditAccountTests {

	private static final String STOCK = "stock";
	private static final String DONATION = "donation";
	private static final String WORK = "work";
	private static final String SALARY = "salary";

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
}
