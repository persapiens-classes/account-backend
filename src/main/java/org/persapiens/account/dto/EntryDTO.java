package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record EntryDTO(long id, @NotBlank String owner, @NotBlank LocalDateTime date, @NotBlank AccountDTO inAccount,
		@NotBlank AccountDTO outAccount, @NotBlank BigDecimal value, String note) {
}
