package org.persapiens.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_owner", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
@SuperBuilder
@Getter
@Setter
public class Owner implements Comparable<Owner> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Override
	public int compareTo(Owner o) {
		return this.name.compareTo(o.name);
	}

}
