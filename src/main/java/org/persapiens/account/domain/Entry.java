package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SequenceGenerator(sequenceName = "seq_entry", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = { "owner", "inAccount", "outAccount", "value", "date" })
@ToString
@SuperBuilder
@Getter
@Setter
public class Entry implements Comparable<Entry> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_entry_owner"))
	private Owner owner;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_entry_inAccount"))
	private Account inAccount;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_entry_outAccount"))
	private Account outAccount;

	@Column(nullable = false)
	private BigDecimal value;

	@Column(nullable = false)
	private LocalDateTime date;

	private String note;

	@Override
	public int compareTo(Entry o) {
		return Comparator.comparing(Entry::getDate)
			.thenComparing(Entry::getValue)
			.thenComparing(Entry::getOwner)
			.compare(this, o);
	}

}
