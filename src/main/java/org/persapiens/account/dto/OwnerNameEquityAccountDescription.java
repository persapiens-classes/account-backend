package org.persapiens.account.dto;

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
public class OwnerNameEquityAccountDescription {

	private String ownerName;

	private String equityAccountDescription;

}
