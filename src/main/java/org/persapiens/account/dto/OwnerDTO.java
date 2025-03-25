package org.persapiens.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "owner")
public record OwnerDTO(@NotBlank String name) {

}
