package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EntryInsertUpdateDTO(@NotBlank String inOwner, @NotBlank String outOwner, @NotNull LocalDateTime date, @NotBlank String inAccount,
		@NotBlank String outAccount, @NotNull @PositiveOrZero BigDecimal value, String note) {

}
