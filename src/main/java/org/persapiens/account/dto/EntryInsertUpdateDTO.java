package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record EntryInsertUpdateDTO(@NotBlank String owner, @NotBlank LocalDateTime date, @NotBlank String inAccount,
		@NotBlank String outAccount, @NotBlank @PositiveOrZero BigDecimal value, String note) {

}
