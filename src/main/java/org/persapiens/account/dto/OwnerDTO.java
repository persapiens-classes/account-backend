package org.persapiens.account.dto;

import jakarta.validation.constraints.NotBlank;

public record OwnerDTO(@NotBlank String name) {

}
