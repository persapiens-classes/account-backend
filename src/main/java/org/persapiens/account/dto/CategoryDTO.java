package org.persapiens.account.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(@NotBlank String description) {

}
