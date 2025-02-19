package org.persapiens.account.dto;

import jakarta.validation.constraints.NotBlank;

public record EquityAccountDTO(@NotBlank String description, @NotBlank String category) implements AccountDTOInterface {

}
