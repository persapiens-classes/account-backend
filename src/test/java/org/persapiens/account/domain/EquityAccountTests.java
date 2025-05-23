package org.persapiens.account.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityAccountConstants;

import static org.assertj.core.api.Assertions.assertThat;

class EquityAccountTests {

	@Test
	void equalDescriptionAndCategory() {
		assertThat(EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(EquityCategory.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.build())
			.isEqualTo(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(EquityCategory.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
				.build());
	}

	@Test
	void differentDescriptionAndCategory() {
		assertThat(EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(EquityCategory.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.build())
			.isNotEqualTo(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(EquityCategory.builder().description(EquityAccountConstants.OTHER_ASSETS).build())
				.build());
	}

	@Test
	void differentDescriptionAndEqualCategory() {
		assertThat(EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(EquityCategory.builder().description(EquityAccountConstants.OTHER_ASSETS).build())
			.build())
			.isNotEqualTo(EquityAccount.builder()
				.description(EquityAccountConstants.CHECKING)
				.category(EquityCategory.builder().description(EquityAccountConstants.OTHER_ASSETS).build())
				.build());
	}

	@Test
	void equalDescriptionWithoutCategory() {
		var builder = EquityAccount.builder().description(EquityAccountConstants.CHECKING);
		Assertions.assertThatThrownBy(builder::build).isInstanceOf(NullPointerException.class);
	}

	@Test
	void equalDescriptionAndCategoryAndDifferent() {
		EquityAccount pocket1 = EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(EquityCategory.builder().description(EquityAccountConstants.INDIVIDUAL_ASSETS).build())
			.build();

		EquityAccount pocket2 = EquityAccount.builder()
			.description(EquityAccountConstants.WALLET)
			.category(EquityCategory.builder().description(EquityAccountConstants.CHECKING).build())
			.build();

		assertThat(pocket1).isNotEqualTo(pocket2);
	}

}
