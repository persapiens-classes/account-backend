package org.persapiens.account.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.persapiens.account.common.EquityAccountConstants.WALLET;
import static org.persapiens.account.common.EquityAccountConstants.INDIVIDUAL_ASSETS;
import static org.persapiens.account.common.EquityAccountConstants.OTHER_ASSETS;
import static org.persapiens.account.common.EquityAccountConstants.CHECKING;

public class EquityAccountTests {

	@Test
	public void equalDescriptionAndCategory() {
		assertThat(EquityAccount.builder()
			.description(WALLET)
			.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
			.build())
			.isEqualTo(EquityAccount.builder()
				.description(WALLET)
				.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
				.build());
	}

	@Test
	public void differentDescriptionAndCategory() {
		assertThat(EquityAccount.builder()
			.description(WALLET)
			.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
			.build())
			.isNotEqualTo(EquityAccount.builder()
				.description(WALLET)
				.category(Category.builder().description(OTHER_ASSETS).build())
				.build());
	}

	@Test
	public void differentDescriptionAndEqualCategory() {
		assertThat(EquityAccount.builder()
			.description(WALLET)
			.category(Category.builder().description(OTHER_ASSETS).build())
			.build())
			.isNotEqualTo(EquityAccount.builder()
				.description(CHECKING)
				.category(Category.builder().description(OTHER_ASSETS).build())
				.build());
	}

	@Test
	public void equalDescriptionWithoutCategory() {
		NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
			EquityAccount.builder()
				.description(WALLET)
				.build()
				.equals(EquityAccount.builder().description(CHECKING).build());
		});
		assertThat(thrown).isNotNull();
	}

	@Test
	public void equalDescriptionAndCategoryAndDifferentOwnerEquityAccountInitialValue() {
		OwnerEquityAccountInitialValue ownerEquityAccountInitialValue = OwnerEquityAccountInitialValue.builder()
			.owner(Owner.builder().name("father").build())
			.value(new BigDecimal(100))
			.build();

		EquityAccount pocket1 = EquityAccount.builder()
			.description(WALLET)
			.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
			.ownerEquityAccountInitialValue(ownerEquityAccountInitialValue)
			.build();

		EquityAccount pocket2 = EquityAccount.builder()
			.description(WALLET)
			.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
			.build();

		assertThat(pocket1).isEqualTo(pocket2);
	}

}
