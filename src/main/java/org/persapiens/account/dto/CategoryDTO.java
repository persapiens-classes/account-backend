package org.persapiens.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@ToString
@NoArgsConstructor
public class CategoryDTO {

	private String description;

}
