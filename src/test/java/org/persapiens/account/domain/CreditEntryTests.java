package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;

import static org.assertj.core.api.Assertions.assertThat;

class CreditEntryTests {

	private static final String INDIVIDUAL_ASSET = "individual asset";

	private static final String SALARY_DESCRIPTION = "receive my salary";

	private CreditEntry entry(LocalDateTime date, BigDecimal value, String ownerName, String note) {
		return CreditEntry.builder()
			.note(note)
			.inOwner(Owner.builder().name(ownerName).build())
			.outOwner(Owner.builder().name(ownerName).build())
			.value(value)
			.date(date)
			.inAccount(EquityAccount.builder()
				.description(EquityAccountConstants.WALLET)
				.category(EquityCategory.builder().description(EquityCategoryConstants.CASH).build())
				.build())
			.outAccount(CreditAccount.builder()
				.description(CreditAccountConstants.SALARY)
				.category(CreditCategory.builder().description(INDIVIDUAL_ASSET).build())
				.build())
			.build();
	}

	@Test
	void equalOwnerValueDateInAccountOutAccountWithDifferentDescription() {
		LocalDateTime now = LocalDateTime.now();

		CreditEntry entrySalary1 = entry(now, new BigDecimal(100), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		CreditEntry entrySalary2 = entry(now, new BigDecimal(100), OwnerConstants.FATHER, "other description");

		assertThat(entrySalary1).isEqualTo(entrySalary2);
	}

	@Test
	void equalOwnerValueDateInAccountOutAccountDescriptionWithDifferentValue() {
		LocalDateTime now = LocalDateTime.now();

		CreditEntry entrySalary1 = entry(now, new BigDecimal(200), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		CreditEntry entrySalary2 = entry(now, new BigDecimal(100), OwnerConstants.FATHER, SALARY_DESCRIPTION);

		assertThat(entrySalary1).isNotEqualTo(entrySalary2);
	}

	@Test
	void compareToWithDifferentDates() {
		Set<CreditEntry> entries = new TreeSet<>();

		CreditEntry entrySalary1 = entry(LocalDateTime.now(), new BigDecimal(100), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		CreditEntry entrySalary2 = entry(LocalDateTime.now(), new BigDecimal(100), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		entries.add(entrySalary2);
		entries.add(entrySalary1);

		assertThat(entries.iterator().next()).isEqualTo(entrySalary1);
	}

	@Test
	void compareToWithDifferentValues() {
		LocalDateTime now = LocalDateTime.now();

		Set<CreditEntry> entries = new TreeSet<>();

		CreditEntry entrySalary1 = entry(now, new BigDecimal(1000), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		entries.add(entrySalary1);
		CreditEntry entrySalary2 = entry(now, new BigDecimal(1000), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		entries.add(entrySalary2);

		assertThat(entries.iterator().next()).isEqualTo(entrySalary2);
	}

	@Test
	void compareToWithDifferentOwners() {
		LocalDateTime now = LocalDateTime.now();

		Set<CreditEntry> entries = new TreeSet<>();

		CreditEntry entrySalary1 = entry(now, new BigDecimal(100), OwnerConstants.MOTHER, SALARY_DESCRIPTION);
		entries.add(entrySalary1);
		CreditEntry entrySalary2 = entry(now, new BigDecimal(100), OwnerConstants.FATHER, SALARY_DESCRIPTION);
		entries.add(entrySalary2);

		assertThat(entries.iterator().next()).isEqualTo(entrySalary2);
	}

}
