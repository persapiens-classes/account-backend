package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.common.OwnerConstants;

import static org.assertj.core.api.Assertions.assertThat;

class TransferEntryTests {

	private static final String TRANSFER_ASSET = "individual transfer asset";

	private static final String TRANSFER_DESCRIPTION = "from checking to wallet";

	private TransferEntry transferEntry(LocalDateTime date, BigDecimal value, String ownerName, String note) {
		return TransferEntry.builder()
			.note(note)
			.outOwner(Owner.builder().name(ownerName).build())
			.inOwner(Owner.builder().name(ownerName).build())
			.value(value)
			.date(date)
			.inAccount(EquityAccount.builder()
				.description(EquityAccountConstants.CHECKING)
				.category(EquityCategory.builder().description(EquityCategoryConstants.BANK).build())
				.build())
			.outAccount(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(EquityCategory.builder().description(TRANSFER_ASSET).build())
				.build())
			.build();
	}

	@Test
	void equalOwnerValueDateInAccountOutAccountWithDifferentDescription() {
		LocalDateTime now = LocalDateTime.now();

		TransferEntry entryChecking1 = transferEntry(now, new BigDecimal(100), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		TransferEntry entryChecking2 = transferEntry(now, new BigDecimal(100), OwnerConstants.FATHER,
				"other description");

		assertThat(entryChecking1).isEqualTo(entryChecking2);
	}

	@Test
	void equalOwnerValueDateInAccountOutAccountDescriptionWithDifferentValue() {
		LocalDateTime now = LocalDateTime.now();

		TransferEntry entryChecking1 = transferEntry(now, new BigDecimal(200), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		TransferEntry entryChecking2 = transferEntry(now, new BigDecimal(100), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);

		assertThat(entryChecking1).isNotEqualTo(entryChecking2);
	}

	@Test
	void compareToWithDifferentDates() {
		Set<TransferEntry> entries = new TreeSet<>();

		TransferEntry entryChecking1 = transferEntry(LocalDateTime.now(), new BigDecimal(100), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		TransferEntry entryChecking2 = transferEntry(LocalDateTime.now(), new BigDecimal(100), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		entries.add(entryChecking2);
		entries.add(entryChecking1);

		assertThat(entries.iterator().next()).isEqualTo(entryChecking1);
	}

	@Test
	void compareToWithDifferentValues() {
		LocalDateTime now = LocalDateTime.now();

		Set<TransferEntry> entries = new TreeSet<>();

		TransferEntry entryChecking1 = transferEntry(now, new BigDecimal(1000), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		entries.add(entryChecking1);
		TransferEntry entryChecking2 = transferEntry(now, new BigDecimal(1000), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		entries.add(entryChecking2);

		assertThat(entries.iterator().next()).isEqualTo(entryChecking2);
	}

	@Test
	void compareToWithDifferentOwners() {
		LocalDateTime now = LocalDateTime.now();

		Set<TransferEntry> entries = new TreeSet<>();

		TransferEntry entryChecking1 = transferEntry(now, new BigDecimal(100), OwnerConstants.MOTHER,
				TRANSFER_DESCRIPTION);
		entries.add(entryChecking1);
		TransferEntry entryChecking2 = transferEntry(now, new BigDecimal(100), OwnerConstants.FATHER,
				TRANSFER_DESCRIPTION);
		entries.add(entryChecking2);

		assertThat(entries.iterator().next()).isEqualTo(entryChecking2);
	}

}
