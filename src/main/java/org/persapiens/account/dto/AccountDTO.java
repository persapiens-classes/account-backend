package org.persapiens.account.dto;

import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuperBuilder
@Data
@ToString
@NoArgsConstructor
public class AccountDTO {
    private String description;
    private CategoryDTO category;
}