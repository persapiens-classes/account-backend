package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.util.Comparator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_ownerEquityAccountInitialValue", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = { "value", "owner", "equityAccount" })
@ToString
@SuperBuilder
@Getter
@Setter
public class OwnerEquityAccountInitialValue implements Comparable<OwnerEquityAccountInitialValue> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@Column(nullable = false)
	private BigDecimal value;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_ownerEquityAccountInitialValue_owner"))
	private Owner owner;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_ownerEquityAccountInitialValue_equityAccount"))
	private EquityAccount equityAccount;

	@Override
	public int compareTo(OwnerEquityAccountInitialValue o) {
		return Comparator.comparing(OwnerEquityAccountInitialValue::getValue)
			.thenComparing(OwnerEquityAccountInitialValue::getOwner)
			.thenComparing(OwnerEquityAccountInitialValue::getEquityAccount)
			.compare(this, o);
	}

}
