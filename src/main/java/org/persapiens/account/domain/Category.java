package org.persapiens.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = "description")
@ToString(of = "description")
@SuperBuilder
@Getter
@Setter
public class Category implements Comparable<Category> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@Column(nullable = false, unique = true)
	private String description;

	@Override
	public int compareTo(Category o) {
		return this.description.compareTo(o.getDescription());
	}

}
