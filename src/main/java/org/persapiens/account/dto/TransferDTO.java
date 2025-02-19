package org.persapiens.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record TransferDTO(@NotBlank String debitOwner, @NotBlank String debitAccount, @NotBlank String creditOwner,
		@NotBlank String creditAccount, @NotBlank @PositiveOrZero BigDecimal value) {

}
