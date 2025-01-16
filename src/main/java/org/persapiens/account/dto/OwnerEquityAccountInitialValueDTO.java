package org.persapiens.account.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class OwnerEquityAccountInitialValueDTO {

	private String owner;

	private String equityAccount;

	private BigDecimal value;

}
