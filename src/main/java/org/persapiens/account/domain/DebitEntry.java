package org.persapiens.account.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_debit_entry", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(callSuper = true, of = { "inAccount", "outAccount" })
@ToString(callSuper = true, of = { "inAccount", "outAccount" })
@SuperBuilder
@Getter
@Setter
public class DebitEntry extends Entry<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_debitEntry_inAccount"))
	private DebitAccount inAccount;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_debitEntry_outAccount"))
	private EquityAccount outAccount;

}
