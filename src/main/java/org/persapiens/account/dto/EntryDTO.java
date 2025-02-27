package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EntryDTO(long id, @NotBlank String owner, @NotNull LocalDateTime date, @NotBlank AccountDTO inAccount,
		@NotBlank AccountDTO outAccount, @NotNull BigDecimal value, String note) {
}
