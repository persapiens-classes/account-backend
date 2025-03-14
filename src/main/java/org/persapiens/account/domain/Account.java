package org.persapiens.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@MappedSuperclass
@EqualsAndHashCode(of = { "description" })
@ToString
@SuperBuilder
@Getter
@Setter
public abstract class Account<T extends Category> implements Comparable<Account<T>> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@Column(nullable = false, unique = true)
	private String description;

	public abstract T getCategory();

	public abstract void setCategory(T newCategory);

	@Override
	public int compareTo(Account<T> o) {
		int result = this.description.compareTo(o.description);
		if (result == 0) {
			result = this.getCategory().compareTo(o.getCategory());
		}
		return result;
	}

}
