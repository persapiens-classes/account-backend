package org.persapiens.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "category")
public record CategoryDTO(@NotBlank String description) {

}
