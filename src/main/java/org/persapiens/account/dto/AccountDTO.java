package org.persapiens.account.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountDTO(@NotBlank String description, @NotBlank String category) {

}
