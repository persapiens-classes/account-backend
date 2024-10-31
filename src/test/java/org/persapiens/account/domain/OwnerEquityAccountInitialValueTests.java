package org.persapiens.account.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerEquityAccountInitialValueTests {

	private static final String POCKET = "pocket";
	private static final String INDIVIDUAL_ASSET = "individual asset";
	private static final String BANK = "bank";

	private static final String FATHER = "father";
	private static final String MOTHER = "mother";

	private OwnerEquityAccountInitialValue ownerEquityAccountInitialValue(
		String categoryDescription, String ownerName, BigDecimal value) {
		return OwnerEquityAccountInitialValue.builder()
			.equityAccount(EquityAccount.builder().description(categoryDescription)
				.category(Category.builder().description(INDIVIDUAL_ASSET).build()).build())
			.owner(Owner.builder().name(ownerName).build())
			.value(value)
			.build();
	}

	@Test
	public void equalOwnerEquityAccountValue() {
		assertThat(ownerEquityAccountInitialValue(POCKET, FATHER, new BigDecimal(100)))
			.isEqualTo(ownerEquityAccountInitialValue(POCKET, FATHER, new BigDecimal(100)));
	}

	@Test
	public void equalOwnerEquityAccountAndDifferenteValue() {
		assertThat(ownerEquityAccountInitialValue(POCKET, FATHER, new BigDecimal(100)))
			.isNotEqualTo(ownerEquityAccountInitialValue(POCKET, FATHER, new BigDecimal(999)));
	}

	@Test
	public void equalOwnerValueAndDifferentEquityAccount() {
		assertThat(ownerEquityAccountInitialValue(POCKET, FATHER, new BigDecimal(100)))
			.isNotEqualTo(ownerEquityAccountInitialValue(BANK, FATHER, new BigDecimal(100)));
	}

	@Test
	public void equalEquityAccountValueAndDifferentOwner() {
		assertThat(ownerEquityAccountInitialValue(POCKET, FATHER, new BigDecimal(100)))
			.isNotEqualTo(ownerEquityAccountInitialValue(POCKET, MOTHER, new BigDecimal(100)));
	}
}
