package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTests {

	private static final String TAX = "tax";

	private static final String PROPERTY = "property";

	@Test
	void equalNames() {
		assertThat(Owner.builder().name(OwnerConstants.FATHER).build())
			.isEqualTo(Owner.builder().name(OwnerConstants.FATHER).build());
	}

	@Test
	void equalDescriptionWithDifferentEntries() {
		Entry gasolineEntry = Entry.builder().note(DebitAccountConstants.GASOLINE).build();
		Entry taxEntry = Entry.builder().note(TAX).build();

		OwnerEquityAccountInitialValue walletInitialValue = OwnerEquityAccountInitialValue.builder()
			.equityAccount(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(Category.builder().description(PROPERTY).build())
				.build())
			.value(new BigDecimal(100))
			.build();

		OwnerEquityAccountInitialValue checkingInitialValue = OwnerEquityAccountInitialValue.builder()
			.equityAccount(EquityAccount.builder()
				.description(EquityAccountConstants.CHECKING)
				.category(Category.builder().description(PROPERTY).build())
				.build())
			.value(new BigDecimal(1000))
			.build();

		Owner father1 = Owner.builder()
			.name(OwnerConstants.FATHER)
			.entry(gasolineEntry)
			.entry(taxEntry)
			.ownerEquityAccountInitialValue(walletInitialValue)
			.ownerEquityAccountInitialValue(checkingInitialValue)
			.build();

		Owner father2 = Owner.builder().name(OwnerConstants.FATHER).build();

		assertThat(father1).isEqualTo(father2);
	}

	@Test
	void differentNames() {
		assertThat(Owner.builder().name(OwnerConstants.FATHER).build())
			.isNotEqualTo(Owner.builder().name(OwnerConstants.MOTHER).build());
	}

	@Test
	void compareTo() {
		Set<Owner> owners = new TreeSet<>();

		Owner mother = Owner.builder().name(OwnerConstants.MOTHER).build();
		owners.add(mother);
		Owner father = Owner.builder().name(OwnerConstants.FATHER).build();
		owners.add(father);

		assertThat(owners.iterator().next()).isEqualTo(father);
	}

}
