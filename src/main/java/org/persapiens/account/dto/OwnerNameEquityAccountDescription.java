package org.persapiens.account.dto;

import jakarta.validation.constraints.NotBlank;

public record OwnerNameEquityAccountDescription(@NotBlank String ownerName, @NotBlank String equityAccountDescription) {

}
