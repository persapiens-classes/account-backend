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
public class DebitAccount extends Account {

	/**
	 * Expense transfer. Debit transfer.
	 */
	public static final String EXPENSE_TRANSFER = "expense transfer";

}
