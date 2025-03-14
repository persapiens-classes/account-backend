package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.DebitCategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;

import static org.assertj.core.api.Assertions.assertThat;

class DebitEntryTests {

	private static final String INDIVIDUAL_ASSET = "individual asset";

	private static final String GAS_DESCRIPTION = "buy gas at my gas station";

	private DebitEntry debitEntry(LocalDateTime date, BigDecimal value, String ownerName, String note) {
		return DebitEntry.builder()
			.note(note)
			.inOwner(Owner.builder().name(ownerName).build())
			.outOwner(Owner.builder().name(ownerName).build())
			.date(date)
			.value(value)
			.inAccount(DebitAccount.builder()
				.description(DebitAccountConstants.GASOLINE)
				.category(DebitCategory.builder().description(DebitCategoryConstants.TRANSPORT).build())
				.build())
			.outAccount(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(EquityCategory.builder().description(INDIVIDUAL_ASSET).build())
				.build())
			.build();
	}

	@Test
	void equalOwnerValueDateInAccountOutAccountWithDifferentDescription() {
		LocalDateTime now = LocalDateTime.now();

		DebitEntry entryGasoline1 = debitEntry(now, new BigDecimal(100), OwnerConstants.FATHER, GAS_DESCRIPTION);
		DebitEntry entryGasoline2 = debitEntry(now, new BigDecimal(100), OwnerConstants.FATHER, "other description");

		assertThat(entryGasoline1).isEqualTo(entryGasoline2);
	}

	@Test
	void equalOwnerValueDateInAccountOutAccountDescriptionWithDifferentValue() {
		LocalDateTime now = LocalDateTime.now();

		DebitEntry entryGasoline1 = debitEntry(now, new BigDecimal(200), OwnerConstants.FATHER, GAS_DESCRIPTION);
		DebitEntry entryGasoline2 = debitEntry(now, new BigDecimal(100), OwnerConstants.FATHER, GAS_DESCRIPTION);

		assertThat(entryGasoline1).isNotEqualTo(entryGasoline2);
	}

	@Test
	void compareToWithDifferentDates() {
		Set<DebitEntry> entries = new TreeSet<>();

		DebitEntry entryGasoline1 = debitEntry(LocalDateTime.now(), new BigDecimal(100), OwnerConstants.FATHER,
				GAS_DESCRIPTION);
		DebitEntry entryGasoline2 = debitEntry(LocalDateTime.now(), new BigDecimal(100), OwnerConstants.FATHER,
				GAS_DESCRIPTION);
		entries.add(entryGasoline2);
		entries.add(entryGasoline1);

		assertThat(entries.iterator().next()).isEqualTo(entryGasoline1);
	}

	@Test
	void compareToWithDifferentValues() {
		LocalDateTime now = LocalDateTime.now();

		Set<DebitEntry> entries = new TreeSet<>();

		DebitEntry entryGasoline1 = debitEntry(now, new BigDecimal(1000), OwnerConstants.FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline1);
		DebitEntry entryGasoline2 = debitEntry(now, new BigDecimal(1000), OwnerConstants.FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline2);

		assertThat(entries.iterator().next()).isEqualTo(entryGasoline2);
	}

	@Test
	void compareToWithDifferentOwners() {
		LocalDateTime now = LocalDateTime.now();

		Set<DebitEntry> entries = new TreeSet<>();

		DebitEntry entryGasoline1 = debitEntry(now, new BigDecimal(100), OwnerConstants.MOTHER, GAS_DESCRIPTION);
		entries.add(entryGasoline1);
		DebitEntry entryGasoline2 = debitEntry(now, new BigDecimal(100), OwnerConstants.FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline2);

		assertThat(entries.iterator().next()).isEqualTo(entryGasoline2);
	}

}
