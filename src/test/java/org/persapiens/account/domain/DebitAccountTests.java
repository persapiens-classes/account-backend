package org.persapiens.account.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DebitAccountTests {

	private static final String GASOLINE = "gasoline";
	private static final String TRANSPORT = "transport";
	private static final String BUS = "bus";
	private static final String AIRPLANE = "airplane";

	@Test
	public void equalsDescriptionAndCategory() {
		assertThat(DebitAccount.builder().description(GASOLINE)
				.category(Category.builder().description(TRANSPORT).build()).build())
			.isEqualTo(DebitAccount.builder().description(GASOLINE)
				.category(Category.builder().description(TRANSPORT).build()).build());
	}

	@Test
	public void differentDescriptionAndCategory() {
		assertThat(DebitAccount.builder().description(GASOLINE)
				.category(Category.builder().description(TRANSPORT).build()).build())
			.isNotEqualTo(DebitAccount.builder().description(GASOLINE)
				.category(Category.builder().description(AIRPLANE).build()).build());
	}

	@Test
	public void differentDescriptionAndEqualCategory() {
		assertThat(DebitAccount.builder().description(GASOLINE)
				.category(Category.builder().description(TRANSPORT).build()).build())
			.isNotEqualTo(DebitAccount.builder().description(BUS)
				.category(Category.builder().description(TRANSPORT).build()).build());
	}

	@Test
	public void equalDescriptionWithoutCategory() {
		NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
			DebitAccount.builder().description(GASOLINE).build()
				.equals(DebitAccount.builder().description(GASOLINE).build());
		});
		assertThat(thrown)
			.isNotNull();
	}
}
