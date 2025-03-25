package org.persapiens.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "error", description = "Default pattern of API error")
public record ErrorDTO(@Schema(example = "2025-03-24T15:42:10.123Z") String timestamp,
		@Schema(example = "400", description = "HTTP status") int status,
		@Schema(example = "Useful information about the error.") String message) {

}
