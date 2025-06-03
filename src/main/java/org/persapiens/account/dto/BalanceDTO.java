package org.persapiens.account.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "balance")
public record BalanceDTO(@NotBlank String owner, @NotBlank AccountDTO equityAccount, @NotNull BigDecimal initialValue,
		@NotNull BigDecimal balance) {

}
