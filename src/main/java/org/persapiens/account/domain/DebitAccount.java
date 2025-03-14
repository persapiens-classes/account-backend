package org.persapiens.account.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_debit_account", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(callSuper = true, of="category")
@ToString(callSuper = true, of="category")
@SuperBuilder
@Getter
@Setter
public class DebitAccount extends Account<DebitCategory> {

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_account_category"))
	@NonNull
	private DebitCategory category;

}
