package org.persapiens.account.domain;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityAccountConstants;

import static org.assertj.core.api.Assertions.assertThat;

class EquityAccountTests {

	@Test
	void equalDescriptionAndCategory() {
		assertThat(EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(Category.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.build())
			.isEqualTo(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(Category.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
				.build());
	}

	@Test
	void differentDescriptionAndCategory() {
		assertThat(EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(Category.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.build())
			.isNotEqualTo(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(Category.builder().description(EquityAccountConstants.OTHER_ASSETS).build())
				.build());
	}

	@Test
	void differentDescriptionAndEqualCategory() {
		assertThat(EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(Category.builder().description(EquityAccountConstants.OTHER_ASSETS).build())
			.build())
			.isNotEqualTo(EquityAccount.builder()
				.description(EquityAccountConstants.CHECKING)
				.category(Category.builder().description(EquityAccountConstants.OTHER_ASSETS).build())
				.build());
	}

	@Test
	void equalDescriptionWithoutCategory() {
		Assertions
			.assertThatThrownBy(() -> EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.build()
				.equals(EquityAccount.builder().description(EquityAccountConstants.CHECKING).build()))
			.isInstanceOf(NullPointerException.class);
	}

	@Test
	void equalDescriptionAndCategoryAndDifferentOwnerEquityAccountInitialValue() {
		OwnerEquityAccountInitialValue ownerEquityAccountInitialValue = OwnerEquityAccountInitialValue.builder()
			.owner(Owner.builder().name("father").build())
			.value(new BigDecimal(100))
			.build();

		EquityAccount pocket1 = EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(Category.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.ownerEquityAccountInitialValue(ownerEquityAccountInitialValue)
			.build();

		EquityAccount pocket2 = EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(Category.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.build();

		assertThat(pocket1).isEqualTo(pocket2);
	}

}
