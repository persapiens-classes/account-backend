package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "entry")
public record EntryDTO(long id, @NotBlank String inOwner, @NotBlank String outOwner, @NotNull LocalDateTime date,
		@NotBlank AccountDTO inAccount, @NotBlank AccountDTO outAccount, @NotNull BigDecimal value, String note) {
}
