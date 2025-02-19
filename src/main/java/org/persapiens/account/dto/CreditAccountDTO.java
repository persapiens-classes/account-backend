package org.persapiens.account.dto;

import jakarta.validation.constraints.NotBlank;

public record CreditAccountDTO(@NotBlank String description, @NotBlank String category) implements AccountDTOInterface {

}
