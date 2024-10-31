package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryTests {

	private static final String FATHER = "father";
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
		LocalDateTime hoje = LocalDateTime.now();

		Entry entryGasolina1 = entry(hoje, new BigDecimal(100), FATHER, GAS_DESCRIPTION);
		Entry entryGasolina2 = entry(hoje, new BigDecimal(100), FATHER, "outra descrição qualquer");

		assertThat(entryGasolina1).isEqualTo(entryGasolina2);
	}

	@Test
	public void equalOwnerValueDateInAccountOutAccountDescriptionWithDifferentValue() {
		LocalDateTime hoje = LocalDateTime.now();

		Entry entryGasolina1 = entry(hoje, new BigDecimal(200), FATHER, GAS_DESCRIPTION);
		Entry entryGasolina2 = entry(hoje, new BigDecimal(100), FATHER, GAS_DESCRIPTION);

		assertThat(entryGasolina1).isNotEqualTo(entryGasolina2);
	}
}
