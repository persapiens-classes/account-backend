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

	private OwnerDTO owner;

	private EquityAccountDTO equityAccount;

	private BigDecimal value;

}
