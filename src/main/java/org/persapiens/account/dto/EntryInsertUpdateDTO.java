package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(name = "entryInsertUpdate", description = "entry dto to put and post operations")
public record EntryInsertUpdateDTO(@NotBlank String inOwner, @NotBlank String outOwner, @NotNull LocalDateTime date,
		@NotBlank String inAccount, @NotBlank String outAccount, @NotNull @PositiveOrZero BigDecimal value,
		String note) {

}
