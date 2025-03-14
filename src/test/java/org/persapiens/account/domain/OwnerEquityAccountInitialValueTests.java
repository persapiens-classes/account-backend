package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerEquityAccountInitialValueTests {

	private static final String INDIVIDUAL_ASSET = "individual asset";

	private OwnerEquityAccountInitialValue ownerEquityAccountInitialValue(String categoryDescription, String ownerName,
			BigDecimal value) {
		return OwnerEquityAccountInitialValue.builder()
			.equityAccount(EquityAccount.builder()
				.description(categoryDescription)
				.category(EquityCategory.builder().description(INDIVIDUAL_ASSET).build())
				.build())
			.owner(Owner.builder().name(ownerName).build())
			.value(value)
			.build();
	}

	@Test
	void equalOwnerEquityAccountValue() {
		assertThat(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.FATHER,
				new BigDecimal(100)))
			.isEqualTo(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.FATHER,
					new BigDecimal(100)));
	}

	@Test
	void equalOwnerEquityAccountAndDifferenteValue() {
		assertThat(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.FATHER,
				new BigDecimal(100)))
			.isNotEqualTo(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.FATHER,
					new BigDecimal(999)));
	}

	@Test
	void equalOwnerValueAndDifferentEquityAccount() {
		assertThat(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.FATHER,
				new BigDecimal(100)))
			.isNotEqualTo(ownerEquityAccountInitialValue(EquityAccountConstants.CHECKING, OwnerConstants.FATHER,
					new BigDecimal(100)));
	}

	@Test
	void equalEquityAccountValueAndDifferentOwner() {
		assertThat(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.FATHER,
				new BigDecimal(100)))
			.isNotEqualTo(ownerEquityAccountInitialValue(EquityAccountConstants.WALLET, OwnerConstants.MOTHER,
					new BigDecimal(100)));
	}

	@Test
	void compareToWithDifferentOwners() {
		Set<OwnerEquityAccountInitialValue> initialValues = new TreeSet<>();

		OwnerEquityAccountInitialValue initialMother = ownerEquityAccountInitialValue(EquityAccountConstants.WALLET,
				OwnerConstants.MOTHER, new BigDecimal(100));
		initialValues.add(initialMother);

		OwnerEquityAccountInitialValue initialFather = ownerEquityAccountInitialValue(EquityAccountConstants.WALLET,
				OwnerConstants.FATHER, new BigDecimal(100));
		initialValues.add(initialFather);

		assertThat(initialValues.iterator().next()).isEqualTo(initialFather);
	}

	@Test
	void compareToWithDifferentValues() {
		Set<OwnerEquityAccountInitialValue> initialValues = new TreeSet<>();

		OwnerEquityAccountInitialValue initial999 = ownerEquityAccountInitialValue(EquityAccountConstants.WALLET,
				OwnerConstants.FATHER, new BigDecimal(999));
		initialValues.add(initial999);

		OwnerEquityAccountInitialValue initial100 = ownerEquityAccountInitialValue(EquityAccountConstants.WALLET,
				OwnerConstants.FATHER, new BigDecimal(100));
		initialValues.add(initial100);

		assertThat(initialValues.iterator().next()).isEqualTo(initial100);
	}

	@Test
	void compareToWithDifferentEquityAccounts() {
		Set<OwnerEquityAccountInitialValue> initialValues = new TreeSet<>();

		OwnerEquityAccountInitialValue pocketInitial = ownerEquityAccountInitialValue(EquityAccountConstants.WALLET,
				OwnerConstants.FATHER, new BigDecimal(100));
		initialValues.add(pocketInitial);

		OwnerEquityAccountInitialValue checkingInitial = ownerEquityAccountInitialValue(EquityAccountConstants.CHECKING,
				OwnerConstants.FATHER, new BigDecimal(100));
		initialValues.add(checkingInitial);

		assertThat(initialValues.iterator().next()).isEqualTo(checkingInitial);
	}

}
