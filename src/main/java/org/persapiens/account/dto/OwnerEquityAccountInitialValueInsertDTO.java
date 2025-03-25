package org.persapiens.account.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ownerEquityAccountInitialValueInsert",
		description = "ownerEquityAccountInitialValue for post operation")
public record OwnerEquityAccountInitialValueInsertDTO(@NotBlank String owner, @NotBlank String equityAccount,
		@NotNull BigDecimal value) {

}
