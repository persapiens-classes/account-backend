package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.persapiens.account.common.DebitAccountConstants.GASOLINE;
import static org.persapiens.account.common.DebitAccountConstants.TRANSPORT;
import static org.persapiens.account.common.DebitAccountConstants.AIRPLANE;
import static org.persapiens.account.common.DebitAccountConstants.BUS;

public class DebitAccountTests {

	@Test
	public void equalsDescriptionAndCategory() {
		assertThat(DebitAccount.builder()
			.description(GASOLINE)
			.category(Category.builder().description(TRANSPORT).build())
			.build())
			.isEqualTo(DebitAccount.builder()
				.description(GASOLINE)
				.category(Category.builder().description(TRANSPORT).build())
				.build());
	}

	@Test
	public void differentDescriptionAndCategory() {
		assertThat(DebitAccount.builder()
			.description(GASOLINE)
			.category(Category.builder().description(TRANSPORT).build())
			.build())
			.isNotEqualTo(DebitAccount.builder()
				.description(GASOLINE)
				.category(Category.builder().description(AIRPLANE).build())
				.build());
	}

	@Test
	public void differentDescriptionAndEqualCategory() {
		assertThat(DebitAccount.builder()
			.description(GASOLINE)
			.category(Category.builder().description(TRANSPORT).build())
			.build())
			.isNotEqualTo(DebitAccount.builder()
				.description(BUS)
				.category(Category.builder().description(TRANSPORT).build())
				.build());
	}

	@Test
	public void equalDescriptionWithoutCategory() {
		NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
			DebitAccount.builder()
				.description(GASOLINE)
				.build()
				.equals(DebitAccount.builder().description(GASOLINE).build());
		});
		assertThat(thrown).isNotNull();
	}

	@Test
	public void compareTo() {
		Set<DebitAccount> debitAccounts = new TreeSet<>();

		DebitAccount bus = DebitAccount.builder()
			.description(BUS)
			.category(Category.builder().description(TRANSPORT).build())
			.build();
		debitAccounts.add(bus);
		DebitAccount airplane = DebitAccount.builder()
			.description(AIRPLANE)
			.category(Category.builder().description(TRANSPORT).build())
			.build();
		debitAccounts.add(airplane);
		DebitAccount gasoline = DebitAccount.builder()
			.description(GASOLINE)
			.category(Category.builder().description(TRANSPORT).build())
			.build();
		debitAccounts.add(gasoline);

		assertThat(debitAccounts.iterator().next()).isEqualTo(airplane);
	}

}
