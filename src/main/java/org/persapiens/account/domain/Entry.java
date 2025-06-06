package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = { "inOwner", "outOwner", "value", "date" })
@ToString
@SuperBuilder
@Getter
@Setter
public abstract class Entry<E extends Entry<E, I, J, O, P>, I extends Account<J>, J extends Category, O extends Account<P>, P extends Category>
		implements Comparable<E> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Owner inOwner;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Owner outOwner;

	public abstract I getInAccount();

	public abstract void setInAccount(I newInAccount);

	public abstract O getOutAccount();

	public abstract void setOutAccount(O newOutAccount);

	@Column(nullable = false)
	private BigDecimal value;

	@Column(nullable = false)
	private LocalDateTime date;

	private String note;

	@Override
	public int compareTo(E o) {
		int result = this.getDate().compareTo(o.getDate());
		if (result == 0) {
			result = this.getValue().compareTo(o.getValue());
			if (result == 0) {
				result = this.getInOwner().compareTo(o.getInOwner());
				if (result == 0) {
					result = this.getOutOwner().compareTo(o.getOutOwner());
				}
			}
		}
		return result;
	}

}
