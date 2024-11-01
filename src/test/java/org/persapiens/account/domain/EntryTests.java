package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryTests {

	private static final String FATHER = "father";
	private static final String MOTHER = "mother";
	private static final String TRANSPORT = "transport";
	private static final String INDIVIDUAL_ASSET = "individual asset";
	private static final String GASOLINE = "gasoline";
	private static final String POCKET = "pocket";
	private static final String GAS_DESCRIPTION = "buy gas at my gas station";

	private Entry entry(LocalDateTime date, BigDecimal value, String ownerName, String note) {
		return Entry.builder()
			.note(note)
			.owner(Owner.builder().name(ownerName).build())
			.value(value)
			.date(date)
			.inAccount(DebitAccount.builder()
				.description(GASOLINE).category(Category.builder().description(TRANSPORT).build()).build())
			.outAccount(EquityAccount.builder()
				.description(POCKET).category(Category.builder().description(INDIVIDUAL_ASSET).build()).build())
			.build();
	}

	@Test
	public void equalOwnerValueDateInAccountOutAccountWithDifferentDescription() {
		LocalDateTime now = LocalDateTime.now();

		Entry entryGasoline1 = entry(now, new BigDecimal(100), FATHER, GAS_DESCRIPTION);
		Entry entryGasoline2 = entry(now, new BigDecimal(100), FATHER, "outra descrição qualquer");

		assertThat(entryGasoline1).isEqualTo(entryGasoline2);
	}

	@Test
	public void equalOwnerValueDateInAccountOutAccountDescriptionWithDifferentValue() {
		LocalDateTime now = LocalDateTime.now();

		Entry entryGasoline1 = entry(now, new BigDecimal(200), FATHER, GAS_DESCRIPTION);
		Entry entryGasoline2 = entry(now, new BigDecimal(100), FATHER, GAS_DESCRIPTION);

		assertThat(entryGasoline1).isNotEqualTo(entryGasoline2);
	}

	@Test
	public void compareToWithDifferentDates() {
		Set<Entry> entries = new TreeSet<>();

		Entry entryGasoline1 = entry(LocalDateTime.now(), new BigDecimal(100), FATHER, GAS_DESCRIPTION);
		Entry entryGasoline2 = entry(LocalDateTime.now(), new BigDecimal(100), FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline2);
		entries.add(entryGasoline1);

		assertThat(entries.iterator().next()).isEqualTo(entryGasoline1);
	}

	@Test
	public void compareToWithDifferentValues() {
		LocalDateTime now = LocalDateTime.now();

		Set<Entry> entries = new TreeSet<>();

		Entry entryGasoline1 = entry(now, new BigDecimal(200), FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline1);
		Entry entryGasoline2 = entry(now, new BigDecimal(100), FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline2);

		assertThat(entries.iterator().next()).isEqualTo(entryGasoline2);
	}

	@Test
	public void compareToWithDifferentOwners() {
		LocalDateTime now = LocalDateTime.now();

		Set<Entry> entries = new TreeSet<>();

		Entry entryGasoline1 = entry(now, new BigDecimal(100), MOTHER, GAS_DESCRIPTION);
		entries.add(entryGasoline1);
		Entry entryGasoline2 = entry(now, new BigDecimal(100), FATHER, GAS_DESCRIPTION);
		entries.add(entryGasoline2);

		assertThat(entries.iterator().next()).isEqualTo(entryGasoline2);
	}
}
