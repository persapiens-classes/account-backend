package org.persapiens.account.domain;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class CreditAccount extends Account {

	/**
	 * Income transfer. Credit transfer.
	 */
	public static final String INCOME_TRANSFER = "income transfer";

}
