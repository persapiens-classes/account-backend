package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerTests {

	private static final String FATHER = "father";
	private static final String MOTHER = "mother";
	private static final String GASOLINE = "gasoline";
	private static final String TAX = "tax";
	private static final String WALLET = "wallet";
	private static final String BANK = "bank";
	private static final String PROPERTY = "property";

	@Test
	public void equalNames() {
		assertThat(Owner.builder().name(FATHER).build())
			.isEqualTo(Owner.builder().name(FATHER).build());
	}

	@Test
	public void equalDescriptionWithDifferentEntries() {
		Entry lancamentoGasolina = Entry.builder().note(GASOLINE).build();
		Entry lancamentoIpva = Entry.builder().note(TAX).build();

		OwnerEquityAccountInitialValue valorInicialNaCarteira = OwnerEquityAccountInitialValue.builder()
			.equityAccount(EquityAccount.builder().description(WALLET)
				.category(Category.builder().description(PROPERTY).build()).build())
			.value(new BigDecimal(100))
			.build();

			OwnerEquityAccountInitialValue valorInicialNoBanco = OwnerEquityAccountInitialValue.builder()
			.equityAccount(EquityAccount.builder().description(BANK)
				.category(Category.builder().description(PROPERTY).build()).build())
			.value(new BigDecimal(1000))
			.build();

		Owner papai1 = Owner.builder().name(FATHER)
			.entry(lancamentoGasolina)
			.entry(lancamentoIpva)
			.ownerEquityAccountInitialValue(valorInicialNaCarteira)
			.ownerEquityAccountInitialValue(valorInicialNoBanco)
			.build();

		Owner papai2 = Owner.builder().name(FATHER).build();

		assertThat(papai1).isEqualTo(papai2);
	}

	@Test
	public void differentNames() {
		assertThat(Owner.builder().name(FATHER).build())
			.isNotEqualTo(Owner.builder().name(MOTHER).build());
	}

	@Test
	public void compareTo() {
		Set<Owner> owners = new TreeSet<>();

		Owner mother = Owner.builder().name(MOTHER).build();
		owners.add(mother);
		Owner father = Owner.builder().name(FATHER).build();
		owners.add(father);

		assertThat(owners.iterator().next()).isEqualTo(father);
	}
}
