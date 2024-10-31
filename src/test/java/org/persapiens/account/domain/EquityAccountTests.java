package org.persapiens.account.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EquityAccountTests {

    private static final String WALLET = "wallet";
    private static final String INDIVIDUAL_ASSETS = "individual assets";
    private static final String OTHER_ASSETS = "other assets";
    private static final String BANK = "bank";

    @Test
    public void equalDescriptionAndCategory() {
        assertThat(EquityAccount.builder().description(WALLET)
                .category(Category.builder().description(INDIVIDUAL_ASSETS).build()).build())
			.isEqualTo(EquityAccount.builder().description(WALLET)
				.category(Category.builder().description(INDIVIDUAL_ASSETS).build()).build());
    }

    @Test
    public void differentDescriptionAndCategory() {
        assertThat(EquityAccount.builder().description(WALLET)
                .category(Category.builder().description(INDIVIDUAL_ASSETS).build()).build())
			.isNotEqualTo(EquityAccount.builder().description(WALLET)
				.category(Category.builder().description(OTHER_ASSETS).build()).build());
    }

    @Test
    public void differentDescriptionAndEqualCategory() {
        assertThat(EquityAccount.builder().description(WALLET)
                .category(Category.builder().description(OTHER_ASSETS).build()).build())
			.isNotEqualTo(EquityAccount.builder().description(BANK)
				.category(Category.builder().description(OTHER_ASSETS).build()).build());
    }

    @Test
    public void equalDescriptionWithoutCategory() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            EquityAccount.builder().description(WALLET).build().equals(EquityAccount.builder().description(BANK).build());
        });
        assertThat(thrown)
                .isNotNull();
    }

    @Test
    public void equalDescriptionAndCategoryAndDifferentOwnerEquityAccountInitialValue() {
        OwnerEquityAccountInitialValue ownerEquityAccountInitialValue = OwnerEquityAccountInitialValue.builder()
			.owner(Owner.builder().name("father").build())
			.value(new BigDecimal(100))
			.build();

        EquityAccount pocket1 = EquityAccount.builder().description(WALLET)
			.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
			.ownerEquityAccountInitialValue(ownerEquityAccountInitialValue)
			.build();

        EquityAccount pocket2 = EquityAccount.builder().description(WALLET)
			.category(Category.builder().description(INDIVIDUAL_ASSETS).build())
			.build();

        assertThat(pocket1).isEqualTo(pocket2);
    }}
