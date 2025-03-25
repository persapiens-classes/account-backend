package org.persapiens.account.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ownerEquityAccountInitialValue")
public record OwnerEquityAccountInitialValueDTO(@NotBlank String owner, @NotBlank AccountDTO equityAccount,
		@NotNull BigDecimal value) {

}
