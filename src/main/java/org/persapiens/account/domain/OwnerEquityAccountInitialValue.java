package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.util.Comparator;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = { "value", "owner", "equityAccount" })
@ToString
@SuperBuilder
@Getter
@Setter
public class OwnerEquityAccountInitialValue implements Comparable<OwnerEquityAccountInitialValue> {

	@Builder.Default
	@EmbeddedId
	private OwnerEquityAccountInitialValueId id = new OwnerEquityAccountInitialValueId();

	@Column(nullable = false)
	private BigDecimal value;

	@MapsId("ownerId")
	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_ownerEquityAccountInitialValue_owner"))
	private Owner owner;

	@MapsId("equityAccountId")
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
