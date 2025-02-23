package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.DebitAccountConstants;

import static org.assertj.core.api.Assertions.assertThat;

class DebitAccountTests {

	@Test
	void equalsDescriptionAndCategory() {
		assertThat(DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.build())
			.isEqualTo(DebitAccount.builder()
				.description(DebitAccountConstants.GASOLINE)
				.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
				.build());
	}

	@Test
	void differentDescriptionAndCategory() {
		assertThat(DebitAccount.builder()
			.description(DebitAccountConstants.BUS)
			.category(Category.builder().description(CategoryConstants.HOUSING).build())
			.build())
			.isNotEqualTo(DebitAccount.builder()
				.description(DebitAccountConstants.GASOLINE)
				.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
				.build());
	}

	@Test
	void differentDescriptionAndEqualCategory() {
		assertThat(DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.build())
			.isNotEqualTo(DebitAccount.builder()
				.description(DebitAccountConstants.BUS)
				.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
				.build());
	}

	@Test
	void equalDescriptionWithoutCategory() {
		var builder = DebitAccount.builder().description(DebitAccountConstants.GASOLINE);
		Assertions.assertThatThrownBy(builder::build).isInstanceOf(NullPointerException.class);
	}

	@Test
	void compareTo() {
		Set<DebitAccount> debitAccounts = new TreeSet<>();

		DebitAccount bus = DebitAccount.builder()
			.description(DebitAccountConstants.BUS)
			.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.build();
		debitAccounts.add(bus);
		DebitAccount airplane = DebitAccount.builder()
			.description(DebitAccountConstants.AIRPLANE)
			.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.build();
		debitAccounts.add(airplane);
		DebitAccount gasoline = DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(CategoryConstants.TRANSPORT).build())
			.build();
		debitAccounts.add(gasoline);

		assertThat(debitAccounts.iterator().next()).isEqualTo(airplane);
	}

}
