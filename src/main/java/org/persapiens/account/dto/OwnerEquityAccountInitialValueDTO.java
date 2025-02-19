package org.persapiens.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record OwnerEquityAccountInitialValueDTO(@NotBlank String owner, @NotBlank String equityAccount,
		@NotBlank BigDecimal value) {

}
