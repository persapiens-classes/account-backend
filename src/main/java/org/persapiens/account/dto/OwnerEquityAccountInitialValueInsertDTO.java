package org.persapiens.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OwnerEquityAccountInitialValueInsertDTO(@NotBlank String owner, @NotBlank String equityAccount,
		@NotNull BigDecimal value) {

}
